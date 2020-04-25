package com.example.sanacorapp.Model01;

public class sensor {

    String s_gp, s_tt, s_temp, s_ph, s_sal, s_turb, s_do;

    public sensor(String s_gp, String s_tt,String s_temp, String s_ph, String s_sal, String s_turb, String s_do) {
        this.s_gp = s_gp;
        this.s_tt = s_tt;
        this.s_temp = s_temp;
        this.s_ph = s_ph;
        this.s_sal = s_sal;
        this.s_turb = s_turb;
        this.s_do = s_do;
    }
    public String getS_gp() {
        return s_gp;
    }

    public String getS_tt() {
        return s_tt;
    }

    public String getS_temp() {
        return s_temp;
    }

    public String getS_ph() {
        return s_ph;
    }

    public String getS_sal() {
        return s_sal;
    }

    public String getS_turb() {
        return s_turb;
    }

    public String getS_do() {
        return s_do;
    }
}
