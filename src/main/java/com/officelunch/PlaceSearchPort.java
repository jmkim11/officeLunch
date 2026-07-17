/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.officelunch;

import java.util.List;

/**
 *
 * @author 김정민
 */
public interface PlaceSearchPort {
    List<CompanySearchResult> search(String query);

}
