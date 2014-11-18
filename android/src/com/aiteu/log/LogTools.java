package com.aiteu.log;

public final class LogTools {
	
	private static LogTools sLogTools;
    public final int DEGUB = 0;
    public final int INFO = 1;
    public final int ERROR = 2;
    public final int NOTHING = 3;
    public int level = DEGUB;

    private LogTools() {
    }

    public static LogTools getInstance() {
        if (sLogTools == null) {
        	synchronized (LogTools.class) {
				if (sLogTools == null) {
					sLogTools = new LogTools();
				}
			}
        }
        return sLogTools;
    }

    public void debug(String msg) {
        if (DEGUB >= level) {
            System.out.println(msg);
        }
    }

    public void info(String msg) {
        if (INFO >= level) {
            System.out.println(msg);
        }
    }

    public void error(String msg) {
        if (ERROR >= level) {
            System.out.println(msg);
        }
    }
}
