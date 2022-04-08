package funciones_simples_fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filtro {

    public static <T> ObjectProperty<Predicate<T>> custom(Property propiedad, Predicate<T> predicado){
        ObjectProperty<Predicate<T>>filtro=new SimpleObjectProperty<>();
        filtro.bind(Bindings.createObjectBinding(()->predicado,propiedad));
        return filtro;
    }
    public static <T>ObjectProperty<Predicate<T>> contains(TextField tf, Function<T,Object> funcion){
        Predicate<T> predicado = t->{
            Object res = funcion.apply(t);
            if (res==null){
                return tf.getText().isEmpty();
            }
            String texto = res.toString().toUpperCase();
            String[]palabras=tf.getText().toUpperCase().split(" ");
            return Arrays.asList(palabras).stream().allMatch(palabra->texto.contains(palabra));
        };
        return custom(tf.textProperty(),predicado);
    }
    public static<T>ObjectProperty<Predicate<T>> contains(TextField tf,List<Function<T,Object>>funciones){
        Predicate<T> predicado = t->{
            String texto = funciones.stream().map(m->{
                Object res=m.apply(t);
                return res==null?"":res.toString().toUpperCase();
            }).collect(Collectors.joining(" "));
            String[]palabras=tf.getText().toUpperCase().split(" ");
            return Arrays.asList(palabras).stream().allMatch(palabra->texto.contains(palabra));
        };
        return custom(tf.textProperty(),predicado);
    }
    public static<T>void aplicarFiltros(TableView tabla, ObservableList<T>lista, List<ObjectProperty<Predicate<T>>>filtros){
        FilteredList<T>lista_filtrada = crearListaParaFiltros(tabla,lista);
        lista_filtrada.predicateProperty().bind(Bindings.createObjectBinding(
                ()->filtros.stream().map(r->r.get()).reduce(Predicate::and).orElse(x->true),
                filtros.toArray(new ObjectProperty[0])));
    }
    private static<T> FilteredList<T> crearListaParaFiltros(TableView tabla, ObservableList<T>lista){
        FilteredList<T>lista_filtrada = new FilteredList(lista);
        SortedList sort = new SortedList(lista_filtrada);
        sort.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(sort);
        return lista_filtrada;
    }
}
