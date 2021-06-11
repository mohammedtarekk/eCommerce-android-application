package com.example.ecommerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DPOperations extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "E_COMMERCE";
    private SQLiteDatabase database;

    public DPOperations(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateScheme(db);
        CreateDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");
        db.execSQL("drop table if exists CartProducts");
        onCreate(db);
    }

    private SQLiteDatabase CreateScheme(SQLiteDatabase db){
        db.execSQL("create table Customers(CustID integer primary key, CustName text not null, Username text not null," +
                "Password text not null, Gender text not null, Birthdate text not null, Job text not null)");

        db.execSQL("create table Orders(OrdID integer primary key, OrdDate text not null, Address text," +
                "CustID integer not null, foreign key(CustID) references Customers (CustID))");

        db.execSQL("create table Categories(CatID integer primary key, CatName text not null)");

        db.execSQL("create table Products(ProID integer primary key, ProName text not null, Price integer not null," +
                "Quantity integer, Description text not null, QrCode text, CatID integer not null, foreign key(CatID) references Categories(CatID))");

        db.execSQL("create table OrderDetails(OrdID integer not null, ProID integer not null, Quantity integer, primary key(OrdID, ProID)," +
                "foreign key(OrdID) references Orders(OrdID)," +
                "foreign key(ProID) references Products(ProID))");

        db.execSQL("create table CartProducts (CustID integer not null, ProID integer not null, ProName text not null, ProPrice text not null, " +
                "ProDescription text not null, ProQuantity integer not null, foreign key(CustID) references Customers(CustID) )");

        return db;
    }

    private void CreateDummyData(SQLiteDatabase db){
        // 1. Create Categories
        ContentValues cat1 = new ContentValues();
        cat1.put("CatName", "Mobiles");
        db.insert("Categories",null,cat1);
        ContentValues cat2 = new ContentValues();
        cat1.put("CatName", "Cameras");
        db.insert("Categories",null,cat2);
        ContentValues cat3 = new ContentValues();
        cat1.put("CatName", "Laptops");
        db.insert("Categories",null,cat3);

        // 2. Add Mobiles
        ContentValues p1 = new ContentValues();
        p1.put("ProName","Samsung Galaxy S21");
        p1.put("Price","14899");
        p1.put("Quantity","1");
        p1.put("Description","Dual SIM Mobile - 6.2 inches, 128 GB, 8 GB RAM, 5G - White");
        p1.put("CatID","1");
        db.insert("Products",null, p1);

        ContentValues p2 = new ContentValues();
        p2.put("ProName","Samsung Galaxy Z Flip");
        p2.put("Price","18000");
        p2.put("Quantity","1");
        p2.put("Description","Dual SIM - 256GB, 8GB RAM, 4G LTE - Black");
        p2.put("CatID","1");
        db.insert("Products",null, p2);

        ContentValues p3 = new ContentValues();
        p3.put("ProName","IPhone 11");
        p3.put("Price","23700");
        p3.put("Quantity","1");
        p3.put("Description","Pro Max 256GB 6 GB RAM, Graphite");
        p3.put("CatID","1");
        db.insert("Products",null, p3);

        ContentValues p4 = new ContentValues();
        p4.put("ProName","Xiaomi Redmi Note 9S");
        p4.put("Price","5000");
        p4.put("Quantity","1");
        p4.put("Description","Dual SIM - 6.67 Inch, 128 GB, 6 GB RAM, 4G LTE - Glacier White");
        p4.put("QrCode","6223007311694");
        p4.put("CatID","1");
        db.insert("Products",null, p4);

        ContentValues p5 = new ContentValues();
        p5.put("ProName","Xiaomi Redmi Note 9S");
        p5.put("Price","5000");
        p5.put("Quantity","1");
        p5.put("Description","Dual SIM - 6.67 Inch, 128 GB, 6 GB RAM, 4G LTE - Glacier White");
        p5.put("QrCode","6223007311694");
        p5.put("CatID","1");
        db.insert("Products",null, p5);

        // 2. Add Cameras
        ContentValues p6 = new ContentValues();
        p6.put("ProName","Nikon COOLPIX B500");
        p6.put("Price","4600");
        p6.put("Quantity","1");
        p6.put("Description","Capture stunning photos and videos and share special memories");
        p6.put("CatID","2");
        db.insert("Products",null, p6);

        ContentValues p7 = new ContentValues();
        p7.put("ProName","Fujifilm Finepix XP120");
        p7.put("Price","2500");
        p7.put("Quantity","1");
        p7.put("Description","16.4MP, 20m UnderWater Digital Camera, Yellow");
        p7.put("CatID","2");
        db.insert("Products",null, p7);

        ContentValues p8 = new ContentValues();
        p8.put("ProName","Nikon Coolpix B600");
        p8.put("Price","2780");
        p8.put("Quantity","1");
        p8.put("Description","16 MP 60X Optical Zoom Full HD WIFI Digital Camera Black");
        p8.put("CatID","2");
        db.insert("Products",null, p8);

        ContentValues p9 = new ContentValues();
        p9.put("ProName","Nikon Body Only");
        p9.put("Price","8900");
        p9.put("Quantity","1");
        p9.put("Description","24.2 MP ,1x Optical Zoom and 3.2 Inch Screen - D5600");
        p9.put("CatID","2");
        p9.put("QrCode", "6221133002547");
        db.insert("Products",null, p9);

        // 3. Add Laptops
        ContentValues p10 = new ContentValues();
        p10.put("ProName","Dell VOSTRO 3501");
        p10.put("Price","7000");
        p10.put("Quantity","1");
        p10.put("Description","ntel 10th Gen Core i3-1005G1, 4 GBRAM ,1TB HDD,Intel UHD Graphics, 15.6-Inch");
        p10.put("CatID","3");
        db.insert("Products",null, p10);

        ContentValues p11 = new ContentValues();
        p11.put("ProName","Apple MacBook Pro");
        p11.put("Price","23000");
        p11.put("Quantity","1");
        p11.put("Description","MVVK2 with Touch Bar and Touch ID Laptop, 9th Gen-Intel Core i9, 2.3 Ghz");
        p11.put("CatID","3");
        db.insert("Products",null, p11);

        ContentValues p12 = new ContentValues();
        p12.put("ProName","DELL Latitude 5300");
        p12.put("Price","13700");
        p12.put("Quantity","1");
        p12.put("Description","Intel Core i5-8365U, 8GBRAM, 256GB PCIe NVMe M.2 SSD");
        p12.put("CatID","3");
        db.insert("Products",null, p12);
    }

    public Cursor userLogin(String username, String password){
        database = getReadableDatabase();
        String [] args = {username, password};
        Cursor cursor = database.rawQuery("select CustID, CustName from Customers where Username=? and Password =?", args);

        if(cursor!=null)
            cursor.moveToFirst();

        database.close();
        return cursor;
    }

    public void userSignUp(User user){
        ContentValues values = new ContentValues();
        values.put("CustName", user.getName());
        values.put("Username", user.getUsername());
        values.put("Password", user.getPassword());
        values.put("Gender", user.getGender());
        values.put("Birthdate", user.getBirthdate());
        values.put("Job", user.getJob());
        database = getWritableDatabase();
        database.insert("Customers",null, values);
        database.close();
    }

    public Cursor recoverPassword(String username){
        database = getReadableDatabase();
        String [] args = {username};
        Cursor cursor = database.rawQuery("select Password from Customers where Username = ?", args);

        if(cursor != null)
            cursor.moveToNext();

        database.close();
        return cursor;
    }

    public void addProductToCart(Product cart, int CustID){
        ContentValues row = new ContentValues();
        row.put("CustID", CustID);
        row.put("ProID", cart.getId());
        row.put("ProName", cart.getName());
        row.put("ProPrice", cart.getPrice());
        row.put("ProDescription", cart.getDescription());
        row.put("ProQuantity", cart.getQuantity());
        database = getWritableDatabase();
        database.insert("CartProducts", null, row);
        database.close();
    }

    public Cursor getProductsByCategory(String CatID){
        database = getReadableDatabase();
        String [] args = {CatID};
        Cursor cursor = database.rawQuery("select * from Products where CatID like? ", args);

        if(cursor!=null)
            cursor.moveToFirst();

        database.close();
        return cursor;
    }

    public Cursor getCartProducts(int CustID){
        database = getReadableDatabase();
        String [] args = {String.valueOf(CustID)};
        Cursor cursor = database.rawQuery("select * from CartProducts where CustID = ?", args);

        if(cursor != null)
            cursor.moveToNext();

        database.close();
        return cursor;
    }

    public void deleteProductFromCart(String productName, int CustID){
        database = getWritableDatabase();
        database.delete("CartProducts","ProName = '"+productName+"' and CustID = '"+CustID+"'",null);
        database.close();
    }

    public void updateProductQuantityInCart(Product product, int CustID){
        ContentValues row = new ContentValues();
        row.put("CustID", CustID);
        row.put("ProID", product.getId());
        row.put("ProName", product.getName());
        row.put("ProPrice", product.getPrice());
        row.put("ProDescription", product.getDescription());
        row.put("ProQuantity", product.getQuantity());
        database = getWritableDatabase();
        database.update("CartProducts", row, "ProID = '"+product.getId()+"' and CustID = '"+CustID+"'", null);
        database.close();
    }

    public Cursor productSearch(String productName){
        database = getReadableDatabase();

        String[] args = {"%"+productName+"%"};
        Cursor cursor = database.rawQuery("select * from Products where ProName like?", args);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            database.close();
            return cursor;
        }

        database.close();
        return null;
    }
    
}
