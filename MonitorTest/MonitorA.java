public monitor class MonitorA {
	int full; 
	
	public MonitorA(){
		full = 0;
	}
	
	public void unlock(){
		full = 0;
	}
	
	public void await(){
		waituntil(full == 0);
		full = 1;
	}
	
}