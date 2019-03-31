/**
 * 
 */
package com.lw.web.entity;

import lombok.Data;

/**
 * @author zhailiang
 *
 */
@Data
public class FileInfo {

	private String path;


	public FileInfo(){}

	public FileInfo(String path){
		this.path = path;
	}

}
