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
            String tabBuf = "";

            //add in imports
            writer.println("import java.util.concurrent.locks.*;");

            while ((text = reader.readLine()) != null) {
                //need to cycle through until monitor class
                if (text.contains("monitor class")) {
                    text = text.replace("monitor ", "");
                    inMonitor = true;
                    tabBuf += "\t";
                    writer.println(text);
                    writer.println(tabBuf + "private final Lock mutex = new ReentrantLock();");
                    writer.println(tabBuf + "private final Condition isEmpty = mutex.newCondition();");
                    bracketCounter += 1;
                    text = reader.readLine();
                }
                if (inMonitor == true && text.contains("{")) {
                    bracketCounter += 1;
                    tabBuf += "\t";
                }
                if (inMonitor == true && text.contains("}")) {
                    bracketCounter -= 1;
                    tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                    if (inMonitor && bracketCounter == 0) {
                        inMonitor = false;
                    }
                }
                //then add in additional stuff into the function
                //to satisfy monitor functionality

                if (inMonitor) {
                    if (text.contains(" await(")) {
                        tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                        writer.println(text.replace("{", "") + " throws InterruptedException {");
                        writer.println(tabBuf + "mutex.lock();");
                        writer.println(tabBuf + "try {");
                        tabBuf += "\t";
                        int bCount = 1;
                        while((text = reader.readLine()) != null) {
                            if (text.contains("{")) {
                                tabBuf+="\t";
                                bCount += 1;
                            }
                            if (text.contains("}")) {
                                bCount -= 1;
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                            }
                            if (bCount == 0) {
                                writer.println(tabBuf + "} finally {");
                                tabBuf += "\t";
                                writer.println(tabBuf + "mutex.unlock();");
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                                writer.println(tabBuf + "}");
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                                writer.println(tabBuf + text.replace("\t", ""));
                                break;
                            } else if (text.contains("waituntil(")) {
                                String cond = text.replace("waituntil", "").replace("\t", "");
                                cond = cond.replace(";", "");
                                writer.println(tabBuf + "while (!" + cond + ") {");
                                tabBuf += "\t";
                                writer.println(tabBuf + "isEmpty.await();");
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                                writer.println(tabBuf + "}");
                            } else {
                                writer.println(tabBuf + text.replace("\t",""));
                            }
                        }
                    } else if (text.contains(" unlock(")) {
                        writer.println(text.replace("{", "") + " throws InterruptedException {");
                        writer.println(tabBuf + "mutex.lock();");
                        writer.println(tabBuf + "try {");
                        tabBuf += "\t";
                        int bCount = 1;
                        boolean ret = false;
                        while((text = reader.readLine()) != null) {
                            if (text.contains("{")) {
                                bCount += 1;
                                tabBuf += "\t";
                            }
                            if (text.contains("}")) {
                                bCount -= 1;
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                            }
                            if (bCount == 0) {
                                tabBuf += "\t";
                                if (ret == false) {
                                    writer.println(tabBuf + "isEmpty.signalAll();");
                                }
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                                writer.println(tabBuf + "} finally {");
                                tabBuf += "\t";
                                writer.println(tabBuf + "mutex.unlock();");
                                tabBuf = tabBuf.substring(0, tabBuf.length()-1);
                                writer.println(tabBuf + "}");
                                writer.println(text);
                                break;
                            } else if (text.contains("return ")) {
                                writer.println(tabBuf + "isEmpty.signalAll();");
                                writer.println(tabBuf + text);
                                ret = true;
                            } else {
                                writer.println(tabBuf + text.replace("\t", ""));
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