import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Labirintas {
    static int M;
    static int N;
    static int[][] LAB;
    static int[][] LABKOP;
    static int[] CX = new int[5];
    static int[] CY = new int[5];
    static int[] FX;
    static int[] FY;
    static int UZD = 0;
    static int NAUJA = 0;
    static int X, Y;
    static boolean YRA;
    static String filename;
    static File file = new File("result.txt");
    static BufferedWriter output = null;
    static Scanner scanner = new Scanner(System.in);
    static int lineCounter = 0;
    static int numberCounter = 0;
    static ArrayList<Integer> tempList = new ArrayList<Integer>();
    static int U, V;
    static int K = 0;
    static ArrayList<Integer> keliasT = new ArrayList<Integer>();
    static int counter = 0;
    static ArrayList<Integer> keliasVX = new ArrayList<Integer>();   // kelias virsunemis X
    static ArrayList<Integer> keliasVY = new ArrayList<Integer>();   // kelias virsunemis Y
    static boolean vienintelisEjimas = false;

    public static void atgal(int U, int V){
        int UU = 0;
        int VV = 0;
        LAB[U][V] = LABKOP[U][V];
        K = 5;
        do{
           K = K - 1;
           UU = U + CX[K];
           VV = V + CY[K];
           if ((1 <= UU) && (UU <= M - 1) && (1 <= VV) && (VV <= N - 1)){
               if (LABKOP[UU][VV] == (LABKOP[U][V] - 1)){
                   LAB[UU][VV] = LABKOP[UU][VV];
                   U = UU;
                   V = VV;
                   // viskas atvirksciai, nes is galo i pradines koord eita
                   if (K == 1 || K == 2){
                       K = K + 2; // pi1 -> pi3 arba pi2 -> pi4
                   }else{ // K = 3 arba K = 4
                       K = K - 2; // pi3 -> pi1, pi4 -> pi2
                   }
                   keliasT.add(0, K);
                   keliasVX.add(0, U);
                   keliasVY.add(0, V);
                   K = 5;
               }
           }
        } while(LABKOP[U][V] != 2);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Pasirinkite lenta: 1 -> 7x7, 2 -> 20x15.");
        if (scanner.nextInt() == 1){
            filename = "file1.txt";
        }else{
            filename = "file2.txt";
        }
        BufferedReader br = new BufferedReader(new FileReader(filename));
        output = new BufferedWriter(new FileWriter(file));
        String line;
        int charCounter;
        String character;

        try {
            while ((line = br.readLine()) != null) {
                charCounter = 0;
                numberCounter = 1;
                lineCounter++;
                while (line.length() > charCounter){
                    character = String.valueOf(line.charAt(charCounter));
                    if (!character.equals(" ") && !character.equals('\n')){
                        tempList.add(Integer.parseInt(character));
                        numberCounter++;
                    }
                    charCounter++;
                }
            }
        } finally {
            br.close();
        }
        M = numberCounter;
        N = lineCounter + 1;
        FX = new int[(M - 1)*(N - 1) + 1];
        FY = new int[(M - 1)*(N - 1) + 1];
        int index = 0;
        LAB = new int[numberCounter + 1][lineCounter + 1];
        LABKOP = new int[numberCounter + 1][lineCounter + 1];
        for (int i = lineCounter; i >= 1; i--){
            for (int j = 1; j < numberCounter; j++){
                LAB[j][i] = tempList.get(index);
                LABKOP[j][i] = LAB[j][i];
                index++;
            }
        }
        System.out.println("Įveskite pradinę agento padėtį (x, y) ");
        X = scanner.nextInt();
        Y = scanner.nextInt();
        LABKOP[X][Y] = 2;

        CX[1] = -1; CY[1] = 0;
        CX[2] =  0; CY[2] = -1;
        CX[3] =  1; CY[3] = 0;
        CX[4] =  0; CY[4] = 1;

        System.out.println("1 DALIS. Duomenys");
        System.out.println("  1.1. Labirintas");
        System.out.println();
        output.write("1 DALIS. Duomenys \n");
        output.write("  1.1. Labirintas \n\n");
        printLabirith(LABKOP);
        System.out.println("  1.2. Pradinė padėtis X=" + X + ", Y=" + Y + ". L=2.");
        System.out.println();
        output.write("\n\n");
        output.write("  1.2. Pradinė padėtis X=" + X + ", Y=" + Y + ". L=2.\n\n");

        FX[1] = X;
        FY[1] = Y;
        UZD = 1;
        NAUJA = 1;
        YRA = false;

        System.out.println("2 DALIS. Vykdymas\n");
        output.write("2 DALIS. Vykdymas\n\n");
        System.out.print("BANGA " + 0 + ", žymė L=\"" + LABKOP[X][Y] + "\". ");
        System.out.println("Pradinė padėtis X=" + X + ", Y=" + Y + ", NAUJA=" + NAUJA + "\n");
        output.write("BANGA " + 0 + ", žymė L=\"" + LABKOP[X][Y] + "\". ");
        output.write("Pradinė padėtis X=" + X + ", Y=" + Y + ", NAUJA=" + NAUJA + "\n\n");
        if ((X == 1) || (X == M - 1) || (Y == 1) || (Y == N - 1)){
            YRA = true;
            U = X;
            V = Y;
            vienintelisEjimas = true;
        }
        int prevLabkop = 0;
        if ((X > 1) && (X < M - 1) && (Y > 1) && (Y < N - 1)){
            do {
                X = FX[UZD];
                Y = FY[UZD];
                if (prevLabkop != (LABKOP[X][Y] - 1)) {
                    System.out.println("BANGA " + (LABKOP[X][Y] - 1) + ", žymė L=\"" + (LABKOP[X][Y] + 1) + "\"");
                    output.write("BANGA " + (LABKOP[X][Y] - 1) + ", žymė L=\"" + (LABKOP[X][Y] + 1) + "\"\n");
                    prevLabkop = LABKOP[X][Y] - 1;
                }
                System.out.println("    Uždaroma UZD=" + ++counter + ", X="+ X + ", Y=" + Y + ".");
                --counter;
                output.write("    Uždaroma UZD=" + ++counter + ", X="+ X + ", Y=" + Y + ".\n");
                K = 0;
                do {
                    K = K + 1;
                    U = X + CX[K];
                    V = Y + CY[K];
                    System.out.print("        R" + K + ". X=" + U + ", Y=" + V + ".");
                    output.write("        R" + K + ". X=" + U + ", Y=" + V + ".");
                    if (LABKOP[U][V] == 0) {
                        LABKOP[U][V] = LABKOP[X][Y] + 1;
                        System.out.print(" Laisva.");
                        output.write(" Laisva.");
                        if ((U == 1) || (U == M - 1) || (V == 1) || (V == N - 1)) {
                            YRA = true;
                            keliasVX.add(0, U);
                            keliasVY.add(0, V);
                            System.out.println(" NAUJA=" + (NAUJA + 1) + ". Terminalinė.");
                            output.write(" NAUJA=" + (NAUJA + 1) + ". Terminalinė.\n");
                        } else {
                            NAUJA = NAUJA + 1;
                            System.out.println(" NAUJA=" + NAUJA + ".");
                            output.write(" NAUJA=" + NAUJA + ".\n");
                            FX[NAUJA] = U;
                            FY[NAUJA] = V;
                        }
                    }else {
                        boolean atidarytaUzdaryta = false;
                        for (int i = 1; i < ((N - 1) * (M - 1) + 1); i++){
                            if(FX[i] == U && FY[i] == V){
                                System.out.println(" UŽDARYTA arba ATIDARYTA.");
                                output.write(" UŽDARYTA arba ATIDARYTA.\n");
                                atidarytaUzdaryta = true;
                            }
                        }
                        if (!atidarytaUzdaryta){
                            System.out.println(" Siena.");
                            output.write(" Siena.\n");
                        }
                    }
                } while (!((K == 4) || YRA));
                UZD = UZD + 1;
                System.out.println();
                output.write("\n");
            } while(!((UZD > NAUJA) || YRA));
        }
        System.out.println("3 DALIS. Rezultatai\n");
        output.write("\n\n3 DALIS. Rezultatai\n\n");
        if (YRA){
            System.out.println("  3.1. Kelias rastas.\n");
            output.write("  3.1. Kelias rastas.\n\n");
            if (!vienintelisEjimas) {
                atgal(U, V);
                System.out.println("  3.2. Kelias grafiškai\n");
                output.write("  3.2. Kelias grafiškai\n\n");
                System.out.println("LABCOPY");
                printLabirith(LABKOP);
                System.out.print("  3.3. Kelias taisyklėmis: ");
                for (int i = 0; i < keliasT.size(); i++) {
                    if (i != keliasT.size() - 1) {
                        System.out.print("R" + keliasT.get(i) + ", ");
                    } else {
                        System.out.print("R" + keliasT.get(i) + ".");
                    }
                }
                System.out.println();
                output.write("\n\n  3.3. Kelias taisyklėmis: ");
                for (int i = 0; i < keliasT.size(); i++){
                    if (i != keliasT.size() - 1){
                        output.write("R" + keliasT.get(i) + ", ");
                    }else{
                        output.write("R" + keliasT.get(i) + ".");
                    }
                }
                output.write('\n');
                System.out.print("  3.4. Kelias viršūnėmis: ");
                for (int i = 0; i < keliasVX.size(); i++) {
                    if (i != keliasVX.size() - 1) {
                        System.out.print("[X=" + keliasVX.get(i) + ", " + "Y=" + keliasVY.get(i) + "], ");
                    } else {
                        System.out.print("[X=" + keliasVX.get(i) + ", " + "Y=" + keliasVY.get(i) + "].");
                    }
                }
                System.out.println();
                output.write("  3.4. Kelias viršūnėmis: ");
                for (int i = 0; i < keliasVX.size(); i++){
                    if (i != keliasVX.size() - 1) {
                        output.write("[X=" + keliasVX.get(i) + ", " + "Y=" + keliasVY.get(i) + "], ");
                    }else{
                        output.write("[X=" + keliasVX.get(i) + ", " + "Y=" + keliasVY.get(i) + "].");
                    }
                }
                output.write('\n');
            }
        } else{
            System.out.println("  3.1. Kelias nerastas.");
            output.write("  3.1. Kelias nerastas.");
        }
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printLabirith(int[][] LAB) throws IOException {
        System.out.println("   Y, V ^");
        output.write("   Y, V ^\n");
        for (int i = lineCounter; i > 0; i--) {
            System.out.print(String.format("%7s", + i) + " | ");
            output.write(String.format("%7s", + i) + " | ");
            for (int j = 1; j < numberCounter; j++) {
                System.out.print(String.format("%3s", LAB[j][i]) + "  ");
                output.write(String.format("%3s", LAB[j][i]) + "  ");
            }
            System.out.println();
            output.write('\n');
        }
        System.out.print("        ");
        output.write("        ");
        for (int i = 0; i < numberCounter; i++){
            System.out.print("-----");
            output.write("-----");
        }
        System.out.println("> X, U");
        output.write("> X, U\n");
        System.out.print("          ");
        output.write("          ");
        for (int j = 1; j < numberCounter; j++) {
            System.out.print(String.format("%3s", j) + "  ");
            output.write(String.format("%3s", j) + "  ");
        }
        System.out.println();
        System.out.println();
    }
}
