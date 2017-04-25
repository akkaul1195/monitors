import java.io.*;

public class JavaProcessor {
    public static void main(String[] args) {
        File file = new File(args[0]);
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            String fileName = args[0];
            fileName = fileName.replace(".notjava", ".java");
            writer = new PrintWriter(fileName, "UTF-8");
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            boolean inMonitor = false;
            int bracketCounter = 0;

            //add in imports
            writer.println("import java.util.concurrent.locks.*;");

            while ((text = reader.readLine()) != null) {
                //need to cycle through until monitor class
                if (text.contains("monitor class")) {
                    text = text.replace("monitor ", "");
                    inMonitor = true;
                    writer.println(text);
                    writer.println("private final Lock mutex = new ReentrantLock();");
                    writer.println("private final Condition isEmpty = mutex.newCondition();");
                    bracketCounter += 1;
                    text = reader.readLine();
                }
                if (inMonitor == true && text.contains("{")) {
                    bracketCounter += 1;
                }
                if (inMonitor == true && text.contains("}")) {
                    bracketCounter -= 1;
                    if (inMonitor && bracketCounter == 0) {
                        inMonitor = false;
                    }
                }
                //then add in additional stuff into the function
                //to satisfy monitor functionality

                if (inMonitor) {
                    if (text.contains(" await(")) {
                        writer.println(text.replace("{", "") + "throws InterruptedException {");
                        writer.println("mutex.lock();");
                        writer.println("try {");
                        int bCount = 1;
                        while((text = reader.readLine()) != null) {
                            if (text.contains("{")) {
                                bCount += 1;
                            }
                            if (text.contains("}")) {
                                bCount -= 1;
                            }
                            if (bCount == 0) {
                                writer.println("} finally {");
                                writer.println("mutex.unlock();");
                                writer.println("}");
                                writer.println(text);
                                break;
                            } else if (text.contains("waituntil(")) {
                                String cond = text.replace("waituntil", "");
                                cond = cond.replace(";", "");
                                writer.println("while (!" + cond + ") {");
                                writer.println("isEmpty.await();");
                                writer.println("}");
                            } else {
                                writer.println(text);
                            }
                        }
                    } else if (text.contains(" unlock(")) {
                        writer.println(text.replace("{", "") + "throws InterruptedException {");
                        writer.println("mutex.lock();");
                        writer.println("try {");
                        int bCount = 1;
                        while((text = reader.readLine()) != null) {
                            if (text.contains("{")) {
                                bCount += 1;
                            }
                            if (text.contains("}")) {
                                bCount -= 1;
                            }
                            if (bCount == 0) {
                                writer.println("} finally {");
                                writer.println("mutex.unlock();");
                                writer.println("}");
                                writer.println(text);
                                break;
                            } else if (text.contains("return ")) {
                                writer.println("isEmpty.signalAll();");
                                writer.println(text);
                            } else {
                                writer.println(text);
                            }
                        }
                    } else {
                        writer.println(text);
                    }
                } else {
                    writer.println(text);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }
}