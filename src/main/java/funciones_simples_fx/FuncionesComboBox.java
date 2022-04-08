package funciones_simples_fx;


import javafx.scene.control.ComboBox;

public class FuncionesComboBox {
    public static <T>void searchWithKey(ComboBox<T>cb){
        cb.setOnKeyReleased(e->{
            if (e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
                cb.show();
                int index=cb.getSelectionModel().getSelectedIndex();
                String letra = e.getText().toUpperCase();
                int i=index+1;
                while(i!=index){
                    if (i>=cb.getItems().size()){
                        i=0;
                    }else{
                        T item = cb.getItems().get(i);
                        if (item!=null){
                            if (item.toString().startsWith(letra)){
                                cb.setValue(item);
                                return;
                            }
                        }
                        i++;
                    }
                }
            }
        });
    }
}
