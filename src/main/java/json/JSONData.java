package json;

import funciones_simples_fx.IdFormat;
import identidadesFX.VisitanteFX;
import modelos.VisitanteModel;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONData {
    String id;
    String empresa;
    String contacto;
    String tel;
    String email;
    String cp;
    boolean externo=false;

    public JSONData(String id,String empresa,String contacto, String tel, String email, String cp) throws JSONException {
        setId(id);
        setEmpresa(empresa);
        setContacto(contacto);
        setTel(tel);
        setEmail(email);
        setCp(cp);
    }
    public JSONData(String jsonString) throws JSONException {
        if (jsonString.contains("\n")){
            jsonConstructor3(jsonString.split("\\n"));
        }else if(jsonString.contains("|")){
            jsonConstructor3(jsonString.split("\\|"));
        }else{
            JSONObject res = new JSONObject(jsonString);
            this.jsonConstructor(res);
        }
    }
    public JSONData(JSONObject object) throws JSONException {
        this.jsonConstructor(object);
    }
    public JSONData(VisitanteFX visitante){
        this.jsonConstructor2(visitante);
    }
    private void jsonConstructor(JSONObject object) throws JSONException {
        setId(object.getString("id"));
        setEmpresa(object.getString("empresa"));
        setContacto(object.getString("contacto"));
        setTel(object.getString("tel"));
        setEmail(object.getString("email"));
        setCp(object.getString("cp"));
    }
    private void jsonConstructor2(VisitanteFX visitante){
        setId(visitante.getIdExterno().isEmpty()?IdFormat.buildId(visitante.getId()):visitante.getIdExterno());
        setEmpresa(visitante.getEmpresa());
        setContacto(visitante.getNombre());
        setTel(visitante.getTelefono());
        setEmail(visitante.getMail());
        setCp(visitante.getCp());
    }
    private void jsonConstructor3(String[] valores){
        setId(valores[0]);
        setEmpresa(valores[1]);
        setContacto(valores[2]);
        setTel(valores[3]);
        setEmail(valores[4]);
        setCp(valores[5]);
        setExterno(true);
    }
    public JSONObject toJSON() throws JSONException {
        return new JSONObject().
                    put("id",getId()).
                    put("empresa",getEmpresa()).
                    put("contacto",getContacto()).
                    put("tel",getTel()).
                    put("email",getEmail()).
                    put("cp",getCp());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public boolean isExterno() {
        return externo;
    }

    public void setExterno(boolean externo) {
        this.externo = externo;
    }
}
