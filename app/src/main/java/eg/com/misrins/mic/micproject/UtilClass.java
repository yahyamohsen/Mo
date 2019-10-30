package eg.com.misrins.mic.micproject;

import java.util.ArrayList;

/**
 * Created by MIC on 27/04/2017.
 */

public class UtilClass {
    ArrayList<DTO> List=arrayList = new ArrayList<DTO>();
    static ArrayList<DTO> arrayList;
    static String[] types = {"اختار", "أطباء","مستشفيات","مراكز أشعة","معامل تحاليل","صيدليات",
            "عيادات تخصصية","علاج طبيعي وتأهيل","مستشفيات ومراكز متخصصة","مراكز بصريات",
            "شركات اجهزة تعويضية وسمعية"};


    static Integer getTypesId(String name){
        Integer id = -1;
        switch(name){
            /***no need for doctors and aghza t3widya as it has child***/
            case "مستشفيات": id =2 ;
                break;
            case "مراكز أشعة": id =3 ;
                break;
            case "معامل تحاليل": id = 4;
                break;
            case "صيدليات": id = 5;
                break;
            case "عيادات تخصصية": id = 6;
                break;
            case "علاج طبيعي وتأهيل": id =7 ;
                break;
            case "مستشفيات ومراكز متخصصة": id = 8;
                break;
            case "مراكز بصريات": id = 9;
                break;


            default: id =-1;
        }
        return id;

    }
    //String[] Drtkhsos = {}
    static ArrayList<DTO> getsubDrTypes(){
        arrayList = new ArrayList<DTO>();

        arrayList.add(new DTO(-1, "اختر التخصص"));
        arrayList.add(new DTO(11, "امراض قلب واوعية دموية"));
        arrayList.add(new DTO(12, "جراحة اوعية دموية"));
        arrayList.add(new DTO(13, "جراحة قلب وصدر"));
        arrayList.add(new DTO(14, "انف واذن وحنجرة"));
        arrayList.add(new DTO(15, "جراحة عامة"));
        arrayList.add(new DTO(16, "طب أطفال"));
        arrayList.add(new DTO(17, "جراحة مسالك بولية"));
        arrayList.add(new DTO(18, "امراض نفسية"));
        arrayList.add(new DTO(19, "طب وجراحة العيون"));
        arrayList.add(new DTO(110, "جلدية تناسلية"));
        arrayList.add(new DTO(111, "نساء وتوليد"));
        arrayList.add(new DTO(112, "طب اورام"));
        arrayList.add(new DTO(113, "جراحة اورام"));
        arrayList.add(new DTO(114, "امراض دم"));
        arrayList.add(new DTO(115, "جهاز هضمي وكبد"));
        arrayList.add(new DTO(116, "حساسية ومناعة"));
        arrayList.add(new DTO(117, "سكر وغدد صماء"));
        arrayList.add(new DTO(118, "طب اسنان"));
        arrayList.add(new DTO(119, "جراحة المخ والاعصاب"));
        arrayList.add(new DTO(120, "الامراض الصدرية"));
        arrayList.add(new DTO(121, "جراحة عظام"));
        arrayList.add(new DTO(122, "باطنة وكلى"));
        arrayList.add(new DTO(123, "باطنة عامة"));
        arrayList.add(new DTO(124, "طب طبيعي وروماتيزم"));


        return arrayList;
    }
    static public String getTkhsosName(Integer id){
        getsubDrTypes();
        String name="";
        for(int i =0 ; i<arrayList.size();i++){
            if(arrayList.get(i).getType_id()==id){
                name=arrayList.get(i).getName();
            }
        }
        return name;
    }
    static public ArrayList<String> getSubDoctorslist(){

        getsubDrTypes();

        ArrayList<String> newarrayList = new ArrayList<String>();
        for(int i =0 ; i<arrayList.size();i++){
            newarrayList.add(arrayList.get(i).getName());
        }

        return newarrayList;

    }
    static  Integer getSubDrID(String typename){

        ArrayList<DTO> arrayList =  getsubDrTypes();

        Integer typeID = 0;

        for(int i =0 ; i<arrayList.size();i++){
            if(arrayList.get(i).getName().equals(typename))
                typeID=arrayList.get(i).getType_id();
        }

        return typeID;

    }

    /****For Centers***/
    public ArrayList<String> getsubhostListData(){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("اختر القسم");
        arrayList.add("مراكز جهاز هضمي متخصصة");
        arrayList.add("مراكز قلب متخصصة") ;
        arrayList.add("مراكز ومستشفيات العيون") ;

        return arrayList;
    }
    static Integer getsubhostID(String name){

        Integer id=0;
        if (name.equals("اختر القسم"))
            id=-1;
        if (name.equals("مراكز جهاز هضمي متخصصة"))
            id=81;
        if (name.equals("مراكز قلب متخصصة"))
            id=82;
        else if (name.equals("مراكز ومستشفيات العيون"))
            id=83;

        return id;
    }
     /* public Integer getSubTypeID(String typeName){
          ArrayList<DTO> arrayList=arrayList = new ArrayList<DTO>();
          Integer typeid =0;
        for(int i =0 ; i<arrayList.size();i++){

            if(arrayList.get(i).getName().equals(typeName)){

                typeid = arrayList.get(i).getType_id();
            }
        }

        return typeid;

    }*/


    /************phone***************/
    static public ArrayList<String> phone(String phone){

        phone = phone.trim();
        String[]arr = phone.split("-");
        ArrayList<String> newString  =new ArrayList<>()  ;

        if(arr.length>0)
        {
            for(int i =0; i<arr.length;i++) {
                String temp ="";
                if(arr[i].length()> 5) {
                    if (arr[i].startsWith("1")) {
                        temp = "0"+arr[i];
                    }else if (arr[i].startsWith("2")) {
                        temp = "02"+arr[i];
                    }
                }


                newString.add(temp);
            }
        }
        return newString;
    }

    static public String phoneString(String phone){
        String newString = "";

        if(phone !=null) {
            String[] arr = phone.split("-");

            if (arr.length > 0) {
                for (int i = 0; i < arr.length; i++) {
                    String temp = "";


                    if (arr[i].length() > 5) {
                        if (arr[i].startsWith("1"))
                            temp = "0" + arr[i];
                        else if (arr[i].startsWith("2"))
                            temp = "02" + arr[i];
                        else
                            temp =arr[i];

                    } else{
                        temp =   arr[i];
                    }
                    if (i != arr.length - 1)
                        newString = newString + temp + " - ";
                    else
                        newString = newString + temp;
                }
            }
        }
        return newString;
    }
}
