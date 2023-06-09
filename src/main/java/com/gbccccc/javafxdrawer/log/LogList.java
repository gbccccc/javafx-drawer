package com.gbccccc.javafxdrawer.log;

import java.util.*;

public class LogList {
    private final Stack<Log> dones;
    private final Stack<Log> todos;

    private LogList() {
        this.dones = new Stack<>();
        this.todos = new Stack<>();
    }

    // lazy singleton holder
    private static final class LogListHolder {
        private static final LogList LOG_LIST = new LogList();
    }

    public static LogList getLogList() {
        return LogListHolder.LOG_LIST;
    }

    public void undo() {
        if (dones.empty()) {
            return;
        }

        Log log = dones.pop();
        log.undo();
        todos.push(log);
    }

    public void redo() {
        if (todos.empty()) {
            return;
        }

        Log log = todos.pop();
        log.redo();
        dones.push(log);
    }

    public void addLog(Log log) {
        dones.push(log);
        todos.clear();
    }
}
