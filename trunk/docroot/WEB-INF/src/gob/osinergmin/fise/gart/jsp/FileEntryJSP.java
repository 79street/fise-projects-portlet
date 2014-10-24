package gob.osinergmin.fise.gart.jsp;

import com.liferay.portal.kernel.repository.model.FileEntry;

public class FileEntryJSP {
	
	String nombreArchivo;
	FileEntry fileEntry;
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public FileEntry getFileEntry() {
		return fileEntry;
	}
	public void setFileEntry(FileEntry fileEntry) {
		this.fileEntry = fileEntry;
	}

}
