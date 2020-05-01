package com.qunar.flight.user.base;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class MySampleLayout2 extends LayoutBase<ILoggingEvent> {
    String prefix = null;
    boolean printThreadName = true;
    String special_prefix = "";

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setPrintThreadName(boolean printThreadName) {
        this.printThreadName = printThreadName;
    }

    public String getSpecial_prefix() {
        return special_prefix;
    }

    public void setSpecial_prefix(String special_prefix) {
        this.special_prefix = special_prefix;
    }

    public String doLayout(ILoggingEvent event) {
        StringBuffer sbuf = new StringBuffer(128);
        if (prefix != null) {
            sbuf.append(prefix + ": ");
        }
        if (special_prefix != null) {
            sbuf.append(special_prefix + ": ");
        }
        sbuf.append(event.getTimeStamp() - event.getLoggerContextVO().getBirthTime());
        sbuf.append(" ");
        sbuf.append(event.getLevel());
        if (printThreadName) {
            sbuf.append(" [");
            sbuf.append(event.getThreadName());
            sbuf.append("] ");
        } else {
            sbuf.append(" ");
        }
        sbuf.append(event.getLoggerName());
        sbuf.append(" - ");
        sbuf.append(event.getFormattedMessage());
        sbuf.append("\n");
        return sbuf.toString();
    }
}
