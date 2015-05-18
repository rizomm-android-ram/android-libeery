package com.rizomm.ram.libeery.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.ExtensionMethod;

/**
 * Created by Amaury on 15/05/2015.
 */
@Data
@Getter
public class FavoriteBeer extends Beer implements Serializable{
    int srcType = 1 ;

    public FavoriteBeer beerToFavoriteBeer(Beer b, int srcType){
        FavoriteBeer fb = new FavoriteBeer();
        fb.setId(b.getId());
        fb.setName(b.getName());
        fb.setSrcType(srcType);
        fb.setStyle(b.getStyle());
        fb.setDescription(b.getDescription());
        fb.setLabel_icon(b.getLabel_icon());
        fb.setLabel_medium(b.getLabel_medium());
        fb.setLabel_large(b.getLabel_large());
        fb.setAbv(b.getAbv());
        return fb;
    }
}
