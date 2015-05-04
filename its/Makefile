%.pdf: %.tex 
	latexmk -pdf $<
	echo
	rm -f *.aux *.out *.toc *.log *.lof *.snm *.nav *.fls *.fdb_latexmk


clean:
	echo
	rm -f *.pdf *.aux *.out *.toc *.log *.lof *.snm *.nav *.fls *.fdb_latexmk
