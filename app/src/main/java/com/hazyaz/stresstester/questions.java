package com.hazyaz.stresstester;
import java.util.ArrayList;

public class questions {
    private final ArrayList<Util> questionSet1 = new ArrayList<>();

    public ArrayList<Util> allData() {
        questionSet1.add(new Util("Been upset because of something that happened unexpectedly?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 1));
        questionSet1.add(new Util("Felt that you were unable to control important things in your life?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 2));
        questionSet1.add(new Util("Felt under confident about your ability to handle your personal problems?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 3));
        questionSet1.add(new Util("Felt nervous and 'stressed'?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 4));
        questionSet1.add(new Util("Felt that things were NOT going your way?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 5));
        questionSet1.add(new Util("Found that you could NOT cope with all the things you had to do?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 6));
        questionSet1.add(new Util("Been unable to control irritations in your life?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 7));
        questionSet1.add(new Util("Felt that you were on top of things?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 8));
        questionSet1.add(new Util("Been angered because of things that happened that were out of your control?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 9));
        questionSet1.add(new Util("Felt difficulties were piling up so high that you could not overcome them?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 10));
        questionSet1.add(new Util("Do you have trouble staying focused on the present moment? ", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 11));
        questionSet1.add(new Util(" Do you struggle to fall asleep at night?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 12));
        questionSet1.add(new Util("Do you experience headaches or muscle tension?", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 13));
        questionSet1.add(new Util(" During work hours, do you have a hard time staying focused and concentrating on the task-at-hand? ", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 14));
        questionSet1.add(new Util("Do you feel irritable, annoyed, or angry over trivial issues? ", "Never", "Almost Never", "Sometimes", "Fairly Often", "Very Often", 15));

        return questionSet1;
    }
}
