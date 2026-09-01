
package Rep;
//con lista
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class RepasoConcimientos extends javax.swing.JFrame {

    DefaultComboBoxModel<String> combo;
    DefaultTableModel modelo;
    DefaultListModel<String> lista;
    int filaspares = 0;
    int filasimpares = 0;

    /**
     * Creates new form RepasoConcimientos
     */
    public RepasoConcimientos() {
        initComponents();
        vector();
    }

    public void vector() {
        String vector[] = {"1", "2", "3", "4", "5"};
        String titulos[] = {"Pares", "Impares"};

        combo = new DefaultComboBoxModel<>(vector);
        jCbxNumero.setModel(combo);

        modelo = new DefaultTableModel(titulos, 0);
        jTblNumeros.setModel(modelo);

        lista = new DefaultListModel<>();
        jLstNumeros.setModel(lista);
    }

                        

    private void jCbxNumeroActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String numero = jCbxNumero.getSelectedItem().toString();

        if (lista.contains(numero)) {
            JOptionPane.showMessageDialog(null, "No repitas el mismo numero");
            return;
        }

        int nume = Integer.parseInt(numero);

        if (nume % 2 == 0) {
            if (filaspares >= modelo.getRowCount()) {
                modelo.addRow(new Object[]{"",""});
            } 
            modelo.setValueAt(nume, filaspares, 0);
            filaspares++;
             
            }else{
                    if(filasimpares>= modelo.getRowCount()){
                        modelo.addRow(new Object[]{"",""});
                    }
                    modelo.setValueAt(nume, filasimpares, 1);
                    filasimpares++;
                }
        
                       lista.addElement(numero);



    }  


//sin lista

private void jCbxNumeroActionPerformed(java.awt.event.ActionEvent evt) {                                            
        String numero = jCbxNumero.getSelectedItem().toString();
        
        // -------------------------------------------------------------
        // NUEVA VALIDACIÓN: Buscar repetidos directamente en la tabla
        boolean repetido = false;
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object celdaPar = modelo.getValueAt(i, 0);   // Lee columna 0
            Object celdaImpar = modelo.getValueAt(i, 1); // Lee columna 1
            
            // Verificamos que no sea nulo y que sea igual al número
            if ( (celdaPar != null && celdaPar.toString().equals(numero)) || 
                 (celdaImpar != null && celdaImpar.toString().equals(numero)) ) {
                repetido = true;
                break; // Lo encontró, rompemos el ciclo
            }
        }
        
        if (repetido) {
            JOptionPane.showMessageDialog(null, "No repitas el número " + numero);
            return; // Corta el proceso aquí mismo
        }
        // -------------------------------------------------------------
        
        int nume = Integer.parseInt(numero);
        
        // LÓGICA DE CLASIFICACIÓN SIN ZIGZAG
        if(nume % 2 == 0){
            if (filaPares >= modelo.getRowCount()) {
                modelo.addRow(new Object[]{"", ""}); 
            }
            modelo.setValueAt(numero, filaPares, 0);
            filaPares++;
            
        } else {
            if (filaImpares >= modelo.getRowCount()) {
                modelo.addRow(new Object[]{"", ""}); 
            }
            modelo.setValueAt(numero, filaImpares, 1);
            filaImpares++;
        }
        
        // (Ya no hay lista.addElement al final)
    }

//promedio 

private void jBtnCalcularPromedioActionPerformed(java.awt.event.ActionEvent evt) {
    // 1. Sacamos todo el bloque de texto del TextArea
    String textoCompleto = jTextAreaNumeros.getText();
    
    // 2. PICAMOS EL TEXTO (En este caso, cada vez que haya un salto de línea)
    // Si los separan por comas, usarías: split(",")
    String[] arregloNumeros = textoCompleto.split("\n");
    
    double suma = 0;
    int cantidadValida = 0;
    
    try {
        // 3. RECORREMOS EL ARREGLO PEDACITO POR PEDACITO
        for (int i = 0; i < arregloNumeros.length; i++) {
            
            // .trim() limpia los espacios en blanco invisibles por si acaso
            String textoLimpio = arregloNumeros[i].trim(); 
            
            // Verificamos que no sea una línea vacía
            if (!textoLimpio.isEmpty()) {
                // Convertimos a número y sumamos
                double numero = Double.parseDouble(textoLimpio);
                suma = suma + numero;
                cantidadValida++; // Contamos cuántos números reales procesamos
            }
        }
        
        // 4. CALCULAMOS Y MOSTRAMOS EL PROMEDIO
        if (cantidadValida > 0) {
            double promedio = suma / cantidadValida;
            javax.swing.JOptionPane.showMessageDialog(this, "El promedio es: " + promedio);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No ingresaste ningún número válido.");
        }
        
    } catch (NumberFormatException ex) {
        // El escudo protector por si colaron letras entre los números
        javax.swing.JOptionPane.showMessageDialog(this, "Error: Asegúrate de ingresar solo números.");
    }
}
//promedio 
private void jBtnPromediarActionPerformed(java.awt.event.ActionEvent evt) {
    // 1. Sacas los textos de tus dos cajas
    String textoNota1 = jTxtNota1.getText();
    String textoNota2 = jTxtNota2.getText();
    
    try {
        // 2. Conviertes a decimales (Double) por si acaso ponen 8.5
        double nota1 = Double.parseDouble(textoNota1);
        double nota2 = Double.parseDouble(textoNota2);
        
        // 3. Calculas el promedio (Súper importante los paréntesis)
        double promedio = (nota1 + nota2) / 2;
        
        // 4. Mandas la fila completita a la tabla (Nota1, Nota2, Promedio)
        modelo.addRow(new Object[]{nota1, nota2, promedio});
        
        // 5. Limpias las cajas para la siguiente vez
        jTxtNota1.setText("");
        jTxtNota2.setText("");
        
    } catch (NumberFormatException ex) {
        // El escudo protector por si escriben letras
        javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingresa solo números en las notas.");
    }
}
//crud

private void jBtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
    // 1. Preguntamos qué fila está seleccionada en este momento
    int fila = jTblEstudiantes.getSelectedRow();
    
    // 2. Verificamos que sí haya seleccionado algo
    if (fila >= 0) {
        // 3. Sobrescribimos la información en el Modelo
        // setValueAt(NUEVO_VALOR, NUMERO_DE_FILA, NUMERO_DE_COLUMNA)
        
        modelo.setValueAt(jTxtNombre.getText(), fila, 0); // Actualiza la columna 0 (Nombres)
        modelo.setValueAt(jTxtNota.getText(), fila, 1);   // Actualiza la columna 1 (Notas)
        
        // Limpiamos las cajas
        jTxtNombre.setText("");
        jTxtNota.setText("");
        
    } else {
        javax.swing.JOptionPane.showMessageDialog(this, "Selecciona la fila que deseas editar.");
    }
}
//
Truco Pro para Editar (Opcional pero da puntos extra):
Normalmente, el ingeniero quiere que cuando des clic en la tabla, los datos suban automáticamente a las cajas de texto para que sea fácil modificarlos. Eso se hace dándole clic derecho a la tabla -> Events -> Mouse -> mouseClicked, y pones esto:

Java
int fila = jTblEstudiantes.getSelectedRow();
// Sacamos el valor de la tabla y lo ponemos en la caja
jTxtNombre.setText(modelo.getValueAt(fila, 0).toString());
jTxtNota.setText(modelo.getValueAt(fila, 1).toString());
Con estos bloques ya tienes el CRUD completo. addRow para meter, removeRow para sacar, y setValueAt para chancar. ¡Estás armadísimo para ese examen, bro! Dale con toda.
