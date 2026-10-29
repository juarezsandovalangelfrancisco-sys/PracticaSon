package uvm;

import javax.swing.JLabel;

public class HiloRebotador implements Runnable {
    private JLabel[][] matriz;
    private int fila;
    private int col;
    private int dirFila = 1; // 1 baja, -1 sube
    private int dirCol = 1;  // 1 derecha, -1 izquierda
    private int filasMax;
    private int colsMax;
    private volatile boolean activo = true;

    public HiloRebotador(JLabel[][] matriz, int filaInicial, int colInicial) {
        this.matriz = matriz;
        this.fila = filaInicial;
        this.col = colInicial;
        this.filasMax = matriz.length;
        this.colsMax = matriz[0].length;
    }

    @Override
    public void run() {
        try {
            while (activo) {
                // Sincronizamos para evitar conflictos con otros hilos sobre la matriz
                synchronized (matriz) {
                    // 1. Limpiamos la celda actual (ponemos texto vacío o un punto)
                    matriz[fila][col].setText("");

                    // 2. Calculamos la nueva posición diagonal
                    fila += dirFila;
                    col += dirCol;

                    // 3. Control de rebote en los bordes verticales
                    if (fila <= 0 || fila >= filasMax - 1) {
                        dirFila = -dirFila;
                    }

                    // 4. Control de rebote en los bordes horizontales
                    if (col <= 0 || col >= colsMax - 1) {
                        dirCol = -dirCol;
                    }

                    // 5. Dibujamos el símbolo '@' en la nueva posición
                    matriz[fila][col].setText("@");
                }

                // Velocidad del movimiento del rebotador (en milisegundos)
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void detener() {
        activo = false;
    }
}