package com.pccw.util;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.apache.lucene.util.Version.LUCENE_8_5_0;

public class FileIndexer {
    private static StandardAnalyzer analyzer=new StandardAnalyzer();
    private static String indexDirectory="C:\\CMIS\\code_index_directory";

    public void indexDoc(IndexWriter writer, Path file) throws IOException
    {
        try (InputStream stream = Files.newInputStream(file))
        {
            //Create lucene Document
            Document doc = new Document();

            doc.add(new StringField("path", file.toString(), Field.Store.YES));
            doc.add(new StringField("fileName",file.toFile().getName(),Field.Store.YES));

            doc.add(new TextField("contents", new String(Files.readAllBytes(file)), Field.Store.YES));

            //Updates a document by first deleting the document(s)
            //containing <code>term</code> and then adding the new
            //document.  The delete and then add are atomic as seen
            //by a reader on the same index
            writer.updateDocument(new Term("path", file.toString()), doc);
        }
    }
}
