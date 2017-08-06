package jekirdek.com.t3mobil.utility;

/**
 * Bu class bütün request urlleri içerir. methodlar static yapılar class name üzerinden erişebilir hale gelmiştir
 *
 */
public class RequestURL {

    public static final String baseUrl = "http://www.jekirdek.com/t3mobil/mobileservlet.php?";
    public static final String loginUrl = "servletMethod=loginControl";
    public static final String dersListesiUrl = "servletMethod=getLessonList";

    public static String getOgrenciYoklamaListesiUrl(int studentId,int lessonId){
        String ogrenciYoklamaListesiUrl = baseUrl.concat("servletMethod=getStudentAttendenceList&student={\"id\":\""+studentId+"\"}& lesson={\"id\":\""+lessonId+"\"}");
        System.out.println(ogrenciYoklamaListesiUrl);

        return ogrenciYoklamaListesiUrl;
    }

    /**
     * eğer student hakkında bütün bilgiler biliniyorsa bu method çağrıltı
     * overload edilmiş olan bu method eğer citizenId boş ise urlden çıkarılıp sunulabilir!
     * @param name
     * @param surname
     * @param citizenId
     * @return
     */
    public static String getOgrenciAramaUrl(String name, String surname, String citizenId){
        String ogrenciAramaUrl = baseUrl.concat("servletMethod=searchStudents&student={\"name\":\""+name+"\",\"surname\":\""+surname+"\",\"citizenId\":\""+citizenId+"\"}");
        System.out.println(ogrenciAramaUrl);
        return ogrenciAramaUrl;
    }

    /**
     * citizenId bilinmiyorsa bu method çağrılır parametreler urle yerleştirilmiş hali verilir
     * @param name
     * @param surname
     * @return
     */

    public static String getOgrenciAramaUrl(String name, String surname){
        String ogrenciAramaUrl = baseUrl.concat("servletMethod=searchStudents&student={\"name\":\""+name+"\",\"surname\":\""+surname+"\"}");

        System.out.println(ogrenciAramaUrl.toString());
        return ogrenciAramaUrl;
    }

    /**
     *
     * @param id eğitimci id
     * @param date günün tarihi otomatik alınmalı format : 2017-07-10 / yyyy-mm-dd
     * @return sınıfın tüm öğrenci listesi
     */
    public static String getTumOgrenciListesiUrl(int id, String date){
        String tumOgrenciListesi = baseUrl.concat("servletMethod=getAttendenceList&instructor={\"id\":\"" + id +"\"}&date="+date);
        System.out.println(tumOgrenciListesi.toString());
        return tumOgrenciListesi;
    }

    public static String getYoklamaGuncellemeUrl(int id, int presence){
        String yoklamaGunceleme = baseUrl.concat("servletMethod=updateAttendence&attendence={\"id\":\"" + id +"\",\"presence\":\"" + presence + "\"}");
        System.out.println(yoklamaGunceleme.toString());
        return yoklamaGunceleme;
    }


}
