package jekirdek.com.t3mobil.utility;

/**
 * Bu class bütün request urlleri içerir. methodlar static yapılar class name üzerinden erişebilir hale gelmiştir
 *
 */
public class RequestURL {

    public static final String baseUrl = "http://www.jekirdek.com/t3mobil/mobileservlet.php?";
    public static final String loginUrl = "servletMethod=loginControl";
    public static final String dersListesiUrl = "servletMethod=getLessonList";
    //public static final String tümYoklamaListesiUrl = "servletMethod=getAttendenceList&instructor={\"id\":\"";

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

}
