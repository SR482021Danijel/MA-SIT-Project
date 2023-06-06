package com.ftn.ma_sit_project.Model;

public class StepByStep {
    private String first;
    private String second;
    private String third;
    private String fourth;
    private String fifth;
    private String sixth;
    private String seventh;

    private String response;

    public String getGuestion1(){ return this.first;}

    public String getGuestion2(){ return this.second;}

    public String getGuestion3(){ return this.third;}

    public String getGuestion4(){ return this.fourth;}

    public String getGuestion5(){ return this.fifth;}

    public String getGuestion6(){ return this.sixth;}

    public String getGuestion7(){ return this.seventh;}

    public String getResponse(){ return this.response;}

    public void setQuestion1(String first){ this.first = first;}

    public void setQuestion2(String second){ this.second = second;}

    public void setQuestion3(String third){ this.third = third;}

    public void setQuestion4(String fourth){ this.fourth = fourth;}

    public void setQuestion5(String fifth){ this.fifth = fifth;}

    public void setQuestion6(String sixth){ this.sixth = sixth;}

    public void setQuestion7(String seventh){ this.seventh = seventh;}

    public void setResponse(String response){ this.response = response;}

    public StepByStep( String first, String second, String third, String fourth, String fifth, String sixth, String seventh, String response){
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.response = response;
    }

    public StepByStep(){}
//
//    @Override
//    public String toString() {
//        return "StepByStep{" +
//                "first='" + this.first + '\'' +
//                ", second='" + this.second + '\'' +
//                ", third='" + this.third + '\'' +
//                ", fourth='" + this.fourth + '\'' +
//                ", fifth='" + this.fifth + '\'' +
//                ", sixth='" + this.sixth + '\'' +
//                ", seventh='" + this.seventh + '\'' +
//                ", response='" + this.response + '\'' +
//                '}';
//    }
}
