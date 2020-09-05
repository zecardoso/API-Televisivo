package com.apitelevisivo.service;

import net.sf.jasperreports.engine.JasperPrint;

public interface JasperReportsService {

	JasperPrint imprimeRelatorioDownload(String file);
	byte[] imprimeRelatorioNoNavegador(String file);
}