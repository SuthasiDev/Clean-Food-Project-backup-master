package suthasidev.cleanfoodproject;

public class MyConstant {

//    URL
    private String urlPostUser = "http://androidthai.in.th/tan/php_add_user.php";
    private String urlPostComment = "http://androidthai.in.th/tan/php_add_comment.php";
    private String urlPostRecipe = "http://androidthai.in.th/tan/php_add_recipe.php";
    private String urlPostRestaurant = "http://androidthai.in.th/tan/php_add_restaurant.php";
    private String[] urlAddData = new String[]{
            "http://androidthai.in.th/tan/php_get_user.php",
            "http://androidthai.in.th/tan/php_get_recipe.php",
            "http://androidthai.in.th/tan/php_get_restaurant.php",
            "http://androidthai.in.th/tan/php_get_comment.php"
    };
    private String urlGetCommentWhereRecipe = "http://androidthai.in.th/tan/getValueWhereRecipe.php";

    public String getUrlGetCommentWhereRecipe() {
        return urlGetCommentWhereRecipe;
    }

    public String getUrlPostUser() {
        return urlPostUser;
    }

    public String getUrlPostComment() {
        return urlPostComment;
    }

    public String getUrlPostRecipe() {
        return urlPostRecipe;
    }

    public String getUrlPostRestaurant() {
        return urlPostRestaurant;
    }

    public String[] getUrlAddData() {
        return urlAddData;
    }
} // Main Class
