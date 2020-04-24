package com.example.bolirepeat;
import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;


@Dao
public interface DataDao {
    @Query("select * from Data")
    List<Data> getalldata();

    @Query("select * from Data where title glob '*'||:string|| '*'")
    List <Data> searchdata(@Nullable String string);


    @Query("select * from Data where id is :id")
     Data getsingledata(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Data data);

    @Delete
    void delete(Data data);


}
