/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guatex.capturadorimagen.gestion;

/**
 *
 * @author RGALICIA
 */
public class Cifrado {

    public String Encriptar(String Password) {

        Password = EspacioEnBlanco(Password);

        String VRETCRIPTA = "";
        if (Password.length() == 0) {
            VRETCRIPTA = " ";
        } else {
            int N_0 = 0;
            int N_1 = 0;
            int N_2 = 0;
            int N_3 = 0;

            boolean sigue1 = true;
            boolean sigue2 = true;
            boolean sigue3 = true;
            boolean sigue4 = true;
            while (sigue1) {
                while (sigue2) {
                    boolean rava = false;
                    double numero = 0;
                    while (!rava) {
                        numero = Math.random();
                        if (numero > 0 && numero < 1) {
                            rava = true;
                        }
                    }

                    double mir = numero * 100;
                    N_1 = (((int) mir) % 36);
                    if (N_1 >= 10 && N_1 <= 16) {
                        sigue2 = false;
                    }

                }

                while (sigue3) {
                    boolean rava = false;
                    double numero = 0;
                    while (!rava) {
                        numero = Math.random();
                        if (numero > 0 && numero < 1) {
                            rava = true;
                        }
                    }

                    double mir = numero * 100;
                    N_2 = (((int) mir) % (36 - N_1));
                    if (N_2 >= 3 && N_2 <= 10) {
                        sigue3 = false;
                    }

                }

                while (sigue4) {
                    boolean rava = false;
                    double numero = 0;
                    while (!rava) {
                        numero = Math.random();
                        if (numero > 0 && numero < 1) {
                            rava = true;
                        }
                    }

                    double mir = numero * 100;
                    N_3 = (((int) mir) % (36 - N_1 - N_2));
                    if (N_3 >= 3 && N_3 <= 10) {
                        sigue4 = false;
                    }

                }

                N_0 = N_1 + N_2 + N_3;
                if (N_0 > 4 && N_0 < 37 && N_0 != 32) {
                    sigue1 = false;
                } else {
                    sigue2 = true;
                    sigue3 = true;
                    sigue4 = true;
                }

            }

            for (int L_CONT = 0; L_CONT < 10; L_CONT++) {
                char[] arreglo = Password.substring(L_CONT, L_CONT + 1).toCharArray();
                char MICHAR = arreglo[0];
                int N_X = (int) MICHAR;
                N_X = N_X + N_0;
                MICHAR = (char) N_X;
                VRETCRIPTA = VRETCRIPTA + MICHAR;
            }

            VRETCRIPTA = VRETCRIPTA + (char) (N_1 + 40);
            VRETCRIPTA = VRETCRIPTA + (char) (N_2 + 40);
            VRETCRIPTA = VRETCRIPTA + (char) (N_3 + 40);
        }
        return VRETCRIPTA;
    }

    public String DesEncriptar(String dato, int longitud) {
        String QUECAMPO;
        int N_1;
        int N_2;
        int N_3;
        int N_0;
        String VRETCRIPTO;
        char MICHAR;
        char[] arreglo;
        VRETCRIPTO = "";
        QUECAMPO = dato.trim();
        arreglo = QUECAMPO.substring(longitud, (longitud + 1)).toCharArray();
        MICHAR = arreglo[0];
        N_1 = (int) MICHAR - 40;
        arreglo = QUECAMPO.substring(longitud + 1, (longitud + 2)).toCharArray();
        MICHAR = arreglo[0];
        N_2 = (int) MICHAR - 40;
        arreglo = QUECAMPO.substring(longitud + 2, (longitud + 3)).toCharArray();
        MICHAR = arreglo[0];
        N_3 = (int) MICHAR - 40;
        N_0 = N_1 + N_2 + N_3;
        for (int li = 0; li < longitud; li++) {
            arreglo = QUECAMPO.substring(li, (li + 1)).toCharArray();
            MICHAR = arreglo[0];
            N_1 = (int) MICHAR;
            N_1 = N_1 - N_0;
            MICHAR = (char) N_1;
            if (N_1 != 0) {
                VRETCRIPTO = VRETCRIPTO + MICHAR;
            }
        }
        return VRETCRIPTO;
    }

    public String DesEncriptarOPEPERSONAL(String Password, int longitud) {
        String QUECAMPO;
        int N_1;
        int N_2;
        int N_3;
        int N_0;
        String VRETCRIPTO;
        char MICHAR;
        char[] arreglo;
        VRETCRIPTO = "";
        QUECAMPO = Password.trim();
        arreglo = QUECAMPO.substring(longitud, (longitud + 1)).toCharArray();
        MICHAR = arreglo[0];
        N_1 = (int) MICHAR - 40;
        arreglo = QUECAMPO.substring(longitud + 1, (longitud + 2)).toCharArray();
        MICHAR = arreglo[0];
        N_2 = (int) MICHAR - 40;
        arreglo = QUECAMPO.substring(longitud + 2, (longitud + 3)).toCharArray();
        MICHAR = arreglo[0];
        N_3 = (int) MICHAR - 40;
        N_0 = N_1 + N_2 + N_3;
        for (int li = 0; li < longitud; li++) {
            arreglo = QUECAMPO.substring(li, (li + 1)).toCharArray();
            MICHAR = arreglo[0];
            N_1 = (int) MICHAR;
            N_1 = N_1 - N_0;
            MICHAR = (char) N_1;
            if (N_1 != 0) {
                VRETCRIPTO = VRETCRIPTO + MICHAR;
            }
        }
        return VRETCRIPTO;
    }

    private String EspacioEnBlanco(String Cadena) {
        Cadena = Cadena.trim();
        int lalongid = 10;
        int longicad = Cadena.length();
        int totca = lalongid - longicad;
        for (int a = 0; a < totca; a++) {
            Cadena = Cadena + " ";
        }
        return Cadena;
    }

    public String Encriptar(String Password, int longitud) {

        Password = EspacioEnBlanco(Password);

        String VRETCRIPTA = "";
        if (Password.length() == 0) {
            VRETCRIPTA = " ";
        } else {
            int N_0 = 0;
            int N_1 = 0;
            int N_2 = 0;
            int N_3 = 0;

            boolean sigue1 = true;
            boolean sigue2 = true;
            boolean sigue3 = true;
            boolean sigue4 = true;
            while (sigue1) {
                while (sigue2) {
                    boolean rava = false;
                    double numero = 0;
                    while (!rava) {
                        numero = Math.random();
                        if (numero > 0 && numero < 1) {
                            rava = true;
                        }
                    }

                    double mir = numero * 100;
                    N_1 = (((int) mir) % 36);
                    if (N_1 >= 10 && N_1 <= 16) {
                        sigue2 = false;
                    }

                }

                while (sigue3) {
                    boolean rava = false;
                    double numero = 0;
                    while (!rava) {
                        numero = Math.random();
                        if (numero > 0 && numero < 1) {
                            rava = true;
                        }
                    }

                    double mir = numero * 100;
                    N_2 = (((int) mir) % (36 - N_1));
                    if (N_2 >= 5 && N_2 <= 10) {
                        sigue3 = false;
                    }

                }

                while (sigue4) {
                    boolean rava = false;
                    double numero = 0;
                    while (!rava) {
                        numero = Math.random();
                        if (numero > 0 && numero < 1) {
                            rava = true;
                        }
                    }

                    double mir = numero * 100;
                    N_3 = (((int) mir) % (36 - N_1 - N_2));
                    if (N_3 >= 5 && N_3 <= 10) {
                        sigue4 = false;
                    }

                }

                N_0 = N_1 + N_2 + N_3;
                if (N_0 > 4 && N_0 < 37 && N_0 != 32 && N_0 != 33) {
                    sigue1 = false;
                } else {
                    sigue2 = true;
                    sigue3 = true;
                    sigue4 = true;
                }

            }
//          cambiar el 10 por una variable 
//            System.out.println("......" + N_0);
            for (int L_CONT = 0; L_CONT < longitud; L_CONT++) {
                char[] arreglo = Password.substring(L_CONT, L_CONT + 1).toCharArray();
                char MICHAR = arreglo[0];
                int N_X = (int) MICHAR;
                N_X = N_X + N_0;
                MICHAR = (char) N_X;
                VRETCRIPTA = VRETCRIPTA + MICHAR;
            }

            VRETCRIPTA = VRETCRIPTA + (char) (N_1 + 40);
            VRETCRIPTA = VRETCRIPTA + (char) (N_2 + 40);
            VRETCRIPTA = VRETCRIPTA + (char) (N_3 + 40);
        }
        String respuesta = VRETCRIPTA.replace("^", ",");
        return respuesta;
    }

}
