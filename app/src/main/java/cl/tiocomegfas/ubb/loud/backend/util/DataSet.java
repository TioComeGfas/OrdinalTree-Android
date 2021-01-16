package cl.tiocomegfas.ubb.loud.backend.util;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.constants.Sex;
import cl.tiocomegfas.ubb.loud.backend.model.Person;

public class DataSet {
    private static final DataSet INSTANCE = new DataSet();
    private static final int SEX_FEMALE = 1;
    private static final int SEX_MALE = 2;

    private DataSet(){ }

    /**
     * Lee los dataset del json
     * @param context
     * @return
     */
    private String readJson(Context context, int idResource){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try{
            bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(idResource)));
            String temp;
            while((temp = bufferedReader.readLine()) != null){
                stringBuilder.append(temp);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    private String readNameMale(Context context){
        return readJson(context,R.raw.nm_1k);
    }

    private String readNameFemale(Context context){
        return readJson(context,R.raw.nf_1k);
    }

    private String readLastName(Context context){
        return readJson(context,R.raw.ln_1k);
    }

    private LinkedList<String> loadJson(String json){
        if(TextUtils.isEmpty(json)){
            throw new IllegalStateException("Json empty");
        }

        LinkedList<String> values = new LinkedList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONArray dataArray = root.getJSONArray("data");
            for(int i=0; i < dataArray.length(); i++){
                JSONArray nameArray = dataArray.getJSONArray(i);
                for(int j=0; j < nameArray.length(); j++){
                    values.add(nameArray.getString(j));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return values;
    }

    public static DataSet getInstance(){
        return INSTANCE;
    }

    public LinkedList<Person> load(Context context, int size){
        //carga de los nombre de hombres
        String json = readNameMale(context);
        LinkedList<String> nameMales = loadJson(json);
        int sizeNameMales = nameMales.size();

        //carga de los nombre de mujeres
        json = readNameFemale(context);
        LinkedList<String> nameFemale = loadJson(json);
        int sizeNameFemale = nameFemale.size();

        //carga de los apellidos
        json = readLastName(context);
        LinkedList<String> lastNames = loadJson(json);
        int sizeNameLastName = lastNames.size();

        Random random = new Random();
        LinkedList<Person> persons = new LinkedList<>();
        for(int i = 0; i < size; i++){
            int randomSex = 1 + random.nextInt(2);
            if(randomSex == SEX_FEMALE){
                persons.add(
                        new Person.Builder().
                                setId(i).
                                setName(nameFemale.get(random.nextInt(sizeNameFemale))).
                                setSex(Sex.FEMALE).
                                setLastName(lastNames.get(random.nextInt(sizeNameLastName))).
                                build()
                );
            }else if (randomSex == SEX_MALE){
                persons.add(
                        new Person.Builder().
                                setId(i).
                                setName(nameFemale.get(random.nextInt(sizeNameMales))).
                                setSex(Sex.MALE).
                                setLastName(lastNames.get(random.nextInt(sizeNameLastName))).
                                build()
                );
            }else{
                throw new IllegalStateException("random sex invalid CHECK!!");
            }
        }
        return persons;
    }

}