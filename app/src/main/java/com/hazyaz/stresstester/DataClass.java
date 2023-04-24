package com.hazyaz.stresstester;

public class DataClass {

    public int setProperImage(int N) {
        Integer[] images = {
                R.drawable.enjoy,
                R.drawable.sleep,
                R.drawable.family,
                R.drawable.connect,
                R.drawable.massage,
                R.drawable.your_interest,
                R.drawable.get_help
        };
        return images[N];
    }

    public String getRemedies(int N) {
        String[] Name = {
                "Hurray!, you are okay.",
                "Get enough sleep",
                "Fulfill family demands to reduce stress",
                "Strengthen your social network.\n\u2022 Connect with others",
                "Learn some relaxation techniques.\n\u2022 Meditation\n\u2022 Massage",
                "Pursue your interest. Your interest is " + getPersonInterest(),
                "Ask for help. Consult to Therapist"
        };

        return Name[N];
    }
    public String getVideoUrl(int N) {
        String[] Name = {
                "https://youtube.com/playlist?list=PLslUf24KKkU-cY3fS6Ns4NIm1gGWv7I7V",
                "https://youtube.com/playlist?list=PLslUf24KKkU8-uL4XMaCzmWfybgNHFv2J"
        };

        return Name[N];
    }

    public String getPersonName(){
        return  MainActivity.personName;
    }
    public String getPersonEmail(){
        return  MainActivity.personEmail;
    }
    public String getPersonGender(){
        return  MainActivity.personGender;
    }
    public String getPersonAge(){
        return  MainActivity.personAge;
    }
    public String getPersonInterest(){
        return  MainActivity.personInterest;
    }

}
