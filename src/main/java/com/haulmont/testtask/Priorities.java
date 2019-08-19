package com.haulmont.testtask;

public enum Priorities {
    NORMAL {
        public String toString() {
            return "Нормальный";
        }
    },
    CITO {
        public String toString() {
            return "Срочный";
        }
    },
    STATIM {
        public String toString() {
            return "Немедленный";
        }
    }
}
