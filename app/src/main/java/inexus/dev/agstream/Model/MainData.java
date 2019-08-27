package inexus.dev.agstream.Model;

/**
 * Created by iNexus on 2/20/2018.
 */

public class MainData {

    private String Tag, Title, Image, Body;

    public MainData() {
    }

    public MainData(String tag, String title, String image, String body) {
        Tag = tag;
        Title = title;
        Image = image;
        Body = body;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
