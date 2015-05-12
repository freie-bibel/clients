/* Copyright (C) 2015  Stephan Kreutzer
 *
 * This file is part of sermon_online_vpl2haggai1.
 *
 * sermon_online_vpl2haggai1 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3 or any later version,
 * as published by the Free Software Foundation.
 *
 * sermon_online_vpl2haggai1 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License 3 for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 3
 * along with sermon_online_vpl2haggai1. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @file $/sermon_online_vpl2haggai1.java
 * @brief Convert the verse-per-line format of sermon-online.de to Haggai XML.
 * @author Stephan Kreutzer
 * @since 2015-05-11
 */



import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;



public class sermon_online_vpl2haggai1
{
    public static void main(String args[])
    {
        System.out.print("sermon_online_vpl2haggai1  Copyright (C) 2015  Stephan Kreutzer\n" +
                         "This program comes with ABSOLUTELY NO WARRANTY.\n" +
                         "This is free software, and you are welcome to redistribute it\n" +
                         "under certain conditions. See the GNU Affero General Public\n" +
                         "License 3 or any later version for details. Also, see the source code\n" +
                         "repository https://github.com/free-scriptures/clients/\n" +
                         "and the project website http://www.free-scriptures.org.\n\n");

        String programPath = sermon_online_vpl2haggai1.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        if (args.length < 3)
        {
            System.out.print("Usage:\n" +
                             "\tsermon_online_vpl2haggai1 in-vpl-file job-file out-haggai-file\n\n");

            System.exit(1);
        }


        File vplFile = new File(args[0]);

        if (vplFile.exists() != true)
        {
            System.out.println("sermon_online_vpl2haggai1: Input VPL file '" + vplFile.getAbsolutePath() + "' doesn't exist.");
            System.exit(-1);
        }

        if (vplFile.isFile() != true)
        {
            System.out.println("sermon_online_vpl2haggai1: Input VPL path '" + vplFile.getAbsolutePath() + "' isn't a file.");
            System.exit(-1);
        }

        if (vplFile.canRead() != true)
        {
            System.out.println("sermon_online_vpl2haggai1: Input VPL file '" + vplFile.getAbsolutePath() + "' isn't readable.");
            System.exit(-1);
        }
        
        File jobFile = new File(args[1]);
        
        if (jobFile.exists() != true)
        {
            System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' doesn't exist.");
            System.exit(-1);
        }

        if (jobFile.isFile() != true)
        {
            System.out.println("sermon_online_vpl2haggai1: Job file path '" + jobFile.getAbsolutePath() + "' isn't a file.");
            System.exit(-1);
        }

        if (jobFile.canRead() != true)
        {
            System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' isn't readable.");
            System.exit(-1);
        }


        String bibleCommonName = null;
        String bibleFullName = null;
        String creator = null;
        String description = null;
        String publisher = null;
        List<String> contributors = new ArrayList<String>();
        String date = null;
        String identifier = null;
        String source = null;
        String language = null;
        String coverage = null;
        String rights = null;
        int footnoteMarkCharacterCountMax = -1;
        Map<String, BibleBookInfo> books = new HashMap<String, BibleBookInfo>();

        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(jobFile);
            document.getDocumentElement().normalize();

            {
                NodeList bibleCommonNameNodeList = document.getElementsByTagName("bible-common-name");
                int bibleCommonNameNodeListLength = bibleCommonNameNodeList.getLength();

                if (bibleCommonNameNodeListLength == 1)
                {
                    Node bibleCommonNameNode = bibleCommonNameNodeList.item(0);
                    
                    bibleCommonName = bibleCommonNameNode.getTextContent();
                }
                else if (bibleCommonNameNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'bible-common-name' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'bible-common-name' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList bibleFullNameNodeList = document.getElementsByTagName("bible-full-name");
                int bibleFullNameNodeListLength = bibleFullNameNodeList.getLength();

                if (bibleFullNameNodeListLength == 1)
                {
                    Node bibleFullNameNode = bibleFullNameNodeList.item(0);
                    
                    bibleFullName = bibleFullNameNode.getTextContent();
                }
                else if (bibleFullNameNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'bible-full-name' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'bible-full-name' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList creatorNodeList = document.getElementsByTagName("creator");
                int creatorNodeListLength = creatorNodeList.getLength();

                if (creatorNodeListLength == 1)
                {
                    Node creatorNode = creatorNodeList.item(0);
                    
                    creator = creatorNode.getTextContent();
                }
                else if (creatorNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'creator' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'creator' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList descriptionNodeList = document.getElementsByTagName("description");
                int descriptionNodeListLength = descriptionNodeList.getLength();

                if (descriptionNodeListLength == 1)
                {
                    Node descriptionNode = descriptionNodeList.item(0);
                    
                    description = descriptionNode.getTextContent();
                }
                else if (descriptionNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'description' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'description' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList publisherNodeList = document.getElementsByTagName("publisher");
                int publisherNodeListLength = publisherNodeList.getLength();

                if (publisherNodeListLength == 1)
                {
                    Node publisherNode = publisherNodeList.item(0);
                    
                    publisher = publisherNode.getTextContent();
                }
                else if (publisherNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'publisher' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'publisher' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList contributorNodeList = document.getElementsByTagName("contributor");
                int contributorNodeListLength = contributorNodeList.getLength();

                for (int i = 0; i < contributorNodeListLength; i++)
                {
                    contributors.add(contributorNodeList.item(i).getTextContent());
                }
            }

            {
                NodeList dateNodeList = document.getElementsByTagName("date");
                int dateNodeListLength = dateNodeList.getLength();

                if (dateNodeListLength == 1)
                {
                    Node dateNode = dateNodeList.item(0);
                    
                    date = dateNode.getTextContent();
                }
                else if (dateNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'date' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'date' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList identifierNodeList = document.getElementsByTagName("identifier");
                int identifierNodeListLength = identifierNodeList.getLength();

                if (identifierNodeListLength == 1)
                {
                    Node identifierNode = identifierNodeList.item(0);
                    
                    identifier = identifierNode.getTextContent();
                }
                else if (identifierNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'identifier' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'identifier' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList sourceNodeList = document.getElementsByTagName("source");
                int sourceNodeListLength = sourceNodeList.getLength();

                if (sourceNodeListLength == 1)
                {
                    Node sourceNode = sourceNodeList.item(0);
                    
                    source = sourceNode.getTextContent();
                }
                else if (sourceNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'source' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList languageNodeList = document.getElementsByTagName("language");
                int languageNodeListLength = languageNodeList.getLength();

                if (languageNodeListLength == 1)
                {
                    Node languageNode = languageNodeList.item(0);
                    
                    language = languageNode.getTextContent();
                }
                else if (languageNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'language' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'language' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList coverageNodeList = document.getElementsByTagName("coverage");
                int coverageNodeListLength = coverageNodeList.getLength();

                if (coverageNodeListLength == 1)
                {
                    Node coverageNode = coverageNodeList.item(0);
                    
                    coverage = coverageNode.getTextContent();
                }
                else if (coverageNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'coverage' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList rightsNodeList = document.getElementsByTagName("rights");
                int rightsNodeListLength = rightsNodeList.getLength();

                if (rightsNodeListLength == 1)
                {
                    Node rightsNode = rightsNodeList.item(0);
                    
                    rights = rightsNode.getTextContent();
                }
                else if (rightsNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'rights' entry.");
                    System.exit(-1);
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing the 'rights' entry.");
                    System.exit(-1);
                }
            }

            {
                NodeList footnoteMarkCharacterCountMaxNodeList = document.getElementsByTagName("footnote-mark-character-count-max");
                int footnoteMarkCharacterCountMaxNodeListLength = footnoteMarkCharacterCountMaxNodeList.getLength();

                if (footnoteMarkCharacterCountMaxNodeListLength == 1)
                {
                    Node footnoteMarkCharacterCountMaxNode = footnoteMarkCharacterCountMaxNodeList.item(0);
                    
                    try
                    {
                        footnoteMarkCharacterCountMax = Integer.parseInt(footnoteMarkCharacterCountMaxNode.getTextContent());
                    }
                    catch (NumberFormatException ex)
                    {
                        System.out.println("sermon_online_vpl2haggai1: The 'footnote-mark-character-count-max' option in job file '" + jobFile.getAbsolutePath() + "' doesn't contain a number.");
                        ex.printStackTrace();
                        System.exit(-1);
                    }
                }
                else if (footnoteMarkCharacterCountMaxNodeListLength > 1)
                {
                    System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' contains more than one 'footnote-mark-character-count-max' entry.");
                    System.exit(-1);
                }
                else
                {
                    // Optional.
                }
            }

            {
                NodeList bookNodeList = document.getElementsByTagName("book");
                int bookNodeListLength = bookNodeList.getLength();

                if (bookNodeListLength > 0)
                {
                    for (int i = 0; i < bookNodeListLength; i++)
                    {
                        Node bookNode = bookNodeList.item(i);

                        NamedNodeMap bookNodeAttributes = bookNode.getAttributes();

                        if (bookNodeAttributes == null)
                        {
                            System.out.println("sermon_online_vpl2haggai1: Bible book entry in job file '" + jobFile.getAbsolutePath() + "' is missing all attributes.");
                            System.exit(-1);
                        }

                        Node identifierAttribute = bookNodeAttributes.getNamedItem("identifier");

                        if (identifierAttribute == null)
                        {
                            System.out.println("sermon_online_vpl2haggai1: Bible book entry in job file '" + jobFile.getAbsolutePath() + "' is missing the 'identifier' attribute.");
                            System.exit(-1);
                        }
                        
                        String bookIdentifier = identifierAttribute.getTextContent();
                        
                        if (bookIdentifier.isEmpty() == true)
                        {
                            System.out.println("sermon_online_vpl2haggai1: Bible book entry in job file '" + jobFile.getAbsolutePath() + "' has an empty 'identifier' attribute.");
                            System.exit(-1);
                        }

                        if (books.containsKey(bookIdentifier) != false)
                        {
                            System.out.println("sermon_online_vpl2haggai1: Bible book entry with identifier '" + bookIdentifier + "' specified more than once in job file '" + jobFile.getAbsolutePath() + "'.");
                            System.exit(-1);
                        }

                        Node numberAttribute = bookNodeAttributes.getNamedItem("number");

                        if (numberAttribute == null)
                        {
                            System.out.println("sermon_online_vpl2haggai1: Bible book entry in job file '" + jobFile.getAbsolutePath() + "' is missing the 'number' attribute.");
                            System.exit(-1);
                        }

                        Integer bookNumber = -1;

                        try
                        {
                            bookNumber = Integer.parseInt(numberAttribute.getTextContent());
                        }
                        catch (NumberFormatException ex)
                        {
                            System.out.println("sermon_online_vpl2haggai1: The 'number' attribute of a Bible book entry in job file '" + jobFile.getAbsolutePath() + "' doesn't contain a number.");
                            ex.printStackTrace();
                            System.exit(-1);
                        }

                        if (bookNumber < 0)
                        {
                            System.out.println("sermon_online_vpl2haggai1: The 'number' attribute of a Bible book entry in job file '" + jobFile.getAbsolutePath() + "' contains a negative number.");
                            System.exit(-1);
                        }

                        for (Map.Entry<String, BibleBookInfo> info : books.entrySet())
                        {
                            if (info.getValue().GetBookNumber() == bookNumber)
                            {
                                System.out.println("sermon_online_vpl2haggai1: The Bible book number " + bookNumber + " of the entry '" + bookIdentifier + "' in job file '" + jobFile.getAbsolutePath() + "' is already used by entry '" + info.getValue().GetIdentifier() + "'   '" + jobFile.getAbsolutePath() + "'.");
                                System.exit(-1);
                            }
                        }

                        String fullName = null;

                        {
                            Node fullNameAttribute = bookNodeAttributes.getNamedItem("long-name");

                            if (fullNameAttribute != null)
                            {
                                fullName = fullNameAttribute.getTextContent();
                            }
                        }

                        String shortName = null;

                        {
                            Node shortNameAttribute = bookNodeAttributes.getNamedItem("short-name");

                            if (shortNameAttribute != null)
                            {
                                shortName = shortNameAttribute.getTextContent();
                            }
                        }

                        books.put(bookIdentifier, new BibleBookInfo(bookIdentifier, bookNumber, fullName, shortName));
                    }
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: No bible book entries in job file '" + jobFile.getAbsolutePath() + "'.");
                    System.exit(-1);
                }
            }
        }
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }


        if (bibleCommonName == null ||
            bibleFullName == null ||
            creator == null ||
            description == null ||
            publisher == null ||
            date == null ||
            identifier == null ||
            language == null ||
            rights == null)
        {
            System.out.println("sermon_online_vpl2haggai1: Job file '" + jobFile.getAbsolutePath() + "' is missing at least one entry.");
            System.exit(-1);
        }

        try
        {
            LineNumberReader reader = new LineNumberReader(
                                      new InputStreamReader(
                                      new FileInputStream(vplFile),
                                      "UTF-8"));
            BufferedWriter writer = new BufferedWriter(
                                    new OutputStreamWriter(
                                    new FileOutputStream(new File(args[2])),
                                    "UTF-8"));

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<XMLBIBLE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"haggai_20130620.xsd\" biblename=\"" + EscapeXMLAttributeText(bibleCommonName) + "\" status=\"v\" version=\"haggai_3.0.0.9.1\" revision=\"0\">\n");
            writer.write("  <!-- This file was generated by sermon_online_vpl2haggai1, which is free software licensed under the GNU Affero General Public License 3 or any later version (see https://github.com/free-scriptures/free-scriptures/ and http://www.free-scriptures.org). -->\n");
            writer.write("  <INFORMATION>\n");
            writer.write("    <title>" + EscapeXMLTextContent(bibleFullName) + "</title>\n");
            writer.write("    <creator>" + EscapeXMLTextContent(creator) + "</creator>\n");
            writer.write("    <description>" + EscapeXMLTextContent(description) + "</description>\n");
            writer.write("    <publisher>" + EscapeXMLTextContent(publisher) + "</publisher>\n");

            for (String contributor : contributors)
            {
                writer.write("    <contributor>" + EscapeXMLTextContent(contributor) + "</contributor>\n");
            }

            writer.write("    <date>" + EscapeXMLTextContent(date) + "</date>\n");
            writer.write("    <type>Text</type>\n");
            writer.write("    <format>Haggai XML Bible Markup Language</format>\n");
            writer.write("    <identifier>" + EscapeXMLTextContent(identifier) + "</identifier>\n");

            if (source != null)
            {
                writer.write("    <source>" + EscapeXMLTextContent(source) + "</source>\n");
            }

            writer.write("    <language>" + EscapeXMLTextContent(language) + "</language>\n");
            
            if (coverage != null)
            {
                writer.write("    <coverage>" + EscapeXMLTextContent(coverage) + "</coverage>\n");
            }

            writer.write("    <rights>" + EscapeXMLTextContent(rights) + "</rights>\n");
            writer.write("  </INFORMATION>\n");

            String lastBook = null;
            int lastChapter = 0;

            String line = reader.readLine();

            while (line != null)
            {
                int posBookFrom = line.indexOf('#');
                
                if (posBookFrom >= 0)
                {
                    posBookFrom += "#".length();
                }
                else
                {
                    System.out.println("sermon_online_vpl2haggai1: Line " + reader.getLineNumber() + " is missing the line number separator '#'.");
                    System.exit(-1);
                }

                int posBookTo = line.indexOf(',', posBookFrom);

                if (posBookTo < 0)
                {
                    System.out.println("sermon_online_vpl2haggai1: Line " + reader.getLineNumber() + " is missing the bible book identifier.");
                    System.exit(-1);
                }

                String book = line.substring(posBookFrom, posBookTo);

                if (books.containsKey(book) != true)
                {
                    System.out.println("sermon_online_vpl2haggai1: Bible book identifier '" + book + "' of line " + reader.getLineNumber() + " in '" + vplFile.getAbsolutePath() + "' isn't one of the bible book identifiers configured in job file '" + jobFile.getAbsolutePath() + "'.");
                    System.exit(-1);
                }

                if (book.equals(lastBook) != true)
                {
                    if (lastChapter != 0)
                    {
                        writer.write("    </CHAPTER>\n");
                        lastChapter = 0;
                    }

                    if (lastBook != null)
                    {
                        writer.write("  </BIBLEBOOK>\n");
                    }

                    BibleBookInfo info = books.get(book);

                    if (info.GetEncountered() == false)
                    {
                        info.SetEncountered(true);
                    }
                    else
                    {
                        System.out.println("sermon_online_vpl2haggai1: The Bible book with identifier '" + book + "' was encountered a second time at line " + reader.getLineNumber() + " of the VPL input file '" + vplFile.getAbsolutePath() + "'.");
                        System.exit(-1);
                    }
                    
                    writer.write("  <BIBLEBOOK bnumber=\"" + info.GetBookNumber() + "\"");

                    if (info.GetFullName() != null)
                    {
                        writer.write(" bname=\"" + EscapeXMLAttributeText(info.GetFullName()) + "\"");
                    }

                    if (info.GetShortName() != null)
                    {
                        writer.write(" bsname=\"" + EscapeXMLAttributeText(info.GetShortName()) + "\"");
                    }

                    writer.write(">\n");

                    lastBook = book;
                }

                posBookTo += new String(",").length();

                int posChapter = line.indexOf(',', posBookTo);

                if (posChapter < 0)
                {
                    System.out.println("sermon_online_vpl2haggai1: Line " + reader.getLineNumber() + "' is missing the chapter number.");
                    System.exit(-1);
                }

                String chapter = line.substring(posBookTo, posChapter);

                int chapterNr = 0;

                try
                {
                    chapterNr = Integer.parseInt(chapter);
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                    System.exit(-1);
                }
                
                if (chapterNr != lastChapter)
                {
                    if (lastChapter != 0)
                    {
                        writer.write("    </CHAPTER>\n");
                    }
                    
                    writer.write("    <CHAPTER cnumber=\"" + chapterNr + "\">\n");

                    lastChapter = chapterNr;
                }

                posChapter += new String(",").length();

                int posVerse = line.indexOf('#', posChapter);

                if (posVerse < 0)
                {
                    System.out.println("sermon_online_vpl2haggai1: Line " + reader.getLineNumber() + " is missing the verse number.");
                    System.exit(-1);
                }

                String verse = line.substring(posChapter, posVerse);

                int verseNr = 0;

                try
                {
                    verseNr = Integer.parseInt(verse);
                }
                catch (NumberFormatException ex)
                {
                    ex.printStackTrace();
                    System.exit(-1);
                }

                posVerse += new String("#").length();

                int posInlineVerse = line.indexOf('.', posVerse);

                if (posInlineVerse >= 0)
                {
                    // Consume the inline verse number and ignore it.

                    boolean validInlineVerseNumber = true;

                    try
                    {
                        Integer.parseInt(line.substring(posVerse, posInlineVerse));
                    }
                    catch (NumberFormatException ex)
                    {
                        validInlineVerseNumber = false;
                    }
                    
                    if (validInlineVerseNumber == true)
                    {
                        posInlineVerse += new String(".").length();

                        // 0xC2A0 in UTF-8, 0x00A0 in UTF-16 (UTF-16 is Java's internal format for
                        // characters in a String): Non breaking space.
                        if (line.indexOf('\u00A0', posInlineVerse) == posInlineVerse)
                        {
                            posInlineVerse += new String("\u00A0").length();
                        }
                        else
                        {
                            validInlineVerseNumber = false;
                        }
                    }
                    
                    if (validInlineVerseNumber == true)
                    {
                        posVerse = posInlineVerse;
                    }
                }


                if (posVerse >= line.length())
                {
                    System.out.println("sermon_online_vpl2haggai1: Line " + reader.getLineNumber() + " is missing the text of the verse.");

                    line = reader.readLine();
                    continue;
                }

                writer.write("      <VERSE vnumber=\"" + verseNr + "\">" + ParseLine(line.substring(posVerse), reader.getLineNumber(), footnoteMarkCharacterCountMax) + "</VERSE>\n");

                line = reader.readLine();
            }

            if (lastChapter != 0)
            {
                writer.write("    </CHAPTER>\n");
            }

            if (lastBook != null)
            {
                writer.write("  </BIBLEBOOK>\n");
            }

            writer.write("</XMLBIBLE>\n");

            writer.flush();
            writer.close();
            reader.close();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
        
        for (Map.Entry<String, BibleBookInfo> info : books.entrySet())
        {
            if (info.getValue().GetEncountered() != true)
            {
                System.out.println("sermon_online_vpl2haggai1: The Bible book with identifier '" + info.getValue().GetIdentifier() + "' was specified in job file '" + jobFile.getAbsolutePath() + "', but wasn't present in VPL input file '" + vplFile.getAbsolutePath() + "'.");
            }
        }
    }

    public static String ParseLine(String line, int lineNumber, int footnoteMarkCharacterCountMax)
    {
        int startPos = 0;
        int endPos = -1;
        int footnoteApparatusStartPos = -1;
        int footnoteApparatusEndPos = line.indexOf("++");
        
        if (footnoteApparatusEndPos < 0)
        {
            // No footnotes.
            return EscapeXMLTextContent(line); 
        }

        List<FootnoteMarkInfo> footnoteMarkRefs = new ArrayList<FootnoteMarkInfo>();
        List<FootnoteMarkInfo> footnoteMarks = new ArrayList<FootnoteMarkInfo>();
        Map<String, String> footnotes = new HashMap<String, String>();

        while (true)
        {
            startPos = line.indexOf('-', startPos);

            if (startPos < 0)
            {
                break;
            }

            startPos += new String("-").length();

            endPos = line.indexOf('-', startPos);
            footnoteApparatusStartPos = line.indexOf(')', startPos);

            if (footnoteApparatusStartPos >= 0)
            {
                if (endPos >= 0)
                {
                    if (footnoteApparatusStartPos < endPos)
                    {
                        endPos = footnoteApparatusStartPos;
                    }
                    else
                    {
                        footnoteApparatusStartPos = -1;
                    }
                }
                else
                {
                    endPos = footnoteApparatusStartPos;
                }
            }

            if (endPos < 0)
            {
                break;
            }

            String footnoteMark = line.substring(startPos, endPos);

            if (footnoteMark.isEmpty() == true)
            {
                startPos = endPos;
                footnoteApparatusStartPos = -1;
                endPos = -1;
                continue;
            }

            if (footnoteMarkCharacterCountMax > 0)
            {
                if (footnoteMarkCharacterCountMax < footnoteMark.length())
                {
                    startPos += 1;
                    footnoteApparatusStartPos = -1;
                    endPos = -1;
                    continue;
                }
            }

            if (footnoteMark.contains(" ") == true ||
                footnoteMark.contains("\u00A0") == true)
            {
                startPos += 1;
                footnoteApparatusStartPos = -1;
                endPos = -1;
                continue;
            }

            if (footnoteApparatusStartPos >= 0)
            {
                // Footnote apparatus is reached.
                
                // Let the first footnote apparatus iteration find the footnote
                // apparatus again.
                startPos -= new String("-").length();
                break;
            }

            footnoteMarkRefs.add(new FootnoteMarkInfo(startPos, footnoteMark, endPos));

            startPos = endPos + new String("-").length();
        }

        if (startPos < 0)
        {
            System.out.println("sermon_online_vpl2haggai1: Footnote marks were found, but no footnote text in line " + lineNumber + ".");
            System.exit(-1);
        }


        do
        {
            startPos = line.indexOf('-', startPos);

            if (startPos < 0)
            {
                break;
            }

            startPos += new String("-").length();

            endPos = line.indexOf(')', startPos);

            if (endPos < 0)
            {
                continue;
            }

            String footnoteMark = line.substring(startPos, endPos);

            if (footnoteMark.isEmpty() == true)
            {
                startPos = endPos;
                endPos = -1;
                continue;
            }

            if (footnoteMarkCharacterCountMax > 0)
            {
                if (footnoteMarkCharacterCountMax < footnoteMark.length())
                {
                    startPos += 1;
                    endPos = -1;
                    continue;
                }
            }

            if (footnoteMark.contains(" ") == true ||
                footnoteMark.contains("\u00A0") == true ||
                footnoteMark.contains("-") == true)
            {
                startPos += 1;
                endPos = -1;
                continue;
            }

            footnoteMarks.add(new FootnoteMarkInfo(startPos, footnoteMark, endPos));

        } while(true);

        if (footnoteMarks.isEmpty() == true)
        {
            System.out.println("sermon_online_vpl2haggai1: Footnote marks were found, but no footnote text in line " + lineNumber + ".");
            System.exit(-1);
        }

        int footnoteMarksSize = footnoteMarks.size();

        for (int i = 0; i < footnoteMarksSize; i++)
        {
            startPos = footnoteMarks.get(i).GetEndPos() + new String(")").length();
            endPos = -1;

            if (i + 1 < footnoteMarksSize)
            {
                endPos = footnoteMarks.get(i + 1).GetStartPos() - new String("-").length();
            }
            else
            {
                endPos = footnoteApparatusEndPos;
            }

            if (footnotes.containsKey(footnoteMarks.get(i).GetFootnoteMark()) != true)
            {
                footnotes.put(footnoteMarks.get(i).GetFootnoteMark(), line.substring(startPos + new String(" ").length(), endPos));
            }
            else
            {
                System.out.println("sermon_online_vpl2haggai1: The footnote '" + footnoteMarks.get(i).GetFootnoteMark() + "' in line " + lineNumber + " is specified more than once.");
                System.exit(-1);
            }
        }

        for (FootnoteMarkInfo info : footnoteMarkRefs)
        {
            if (footnotes.containsKey(info.GetFootnoteMark()) != true)
            {
                System.out.println("sermon_online_vpl2haggai1: The footnote mark '" + info.GetFootnoteMark() + "' was used in line " + lineNumber + ", but it's missing its text.");
                System.exit(-1);
            }
        }

        StringBuilder result = new StringBuilder();

        startPos = 0;
        endPos = 0;

        for (FootnoteMarkInfo info : footnoteMarkRefs)
        {
            endPos = info.GetStartPos() - new String("-").length();

            result.append(EscapeXMLTextContent(line.substring(startPos, endPos)));
            result.append("<NOTE>");
            result.append(EscapeXMLTextContent(footnotes.get(info.GetFootnoteMark())));
            result.append("</NOTE>");

            startPos = info.GetEndPos() + new String("-").length();
        }

        // The next '-' to find is the first of the footnote apparatus.        
        endPos = line.indexOf("-", startPos);

        result.append(EscapeXMLTextContent(line.substring(startPos, endPos)));
        result.append(EscapeXMLTextContent(line.substring(footnoteApparatusEndPos + new String("++").length())));

        return result.toString();
    }

    public static String EscapeXMLAttributeText(String attributeText)
    {
        return attributeText.replace("&", "&amp;")
                            .replace("\"", "&quot;");
    }
    
    public static String EscapeXMLTextContent(String textContent)
    {
        return textContent.replace("&", "&amp;")
                          .replace("<", "&lt;")
                          .replace(">", "&gt;");
    }
}

class BibleBookInfo
{
    public BibleBookInfo(String identifier, Integer bookNumber, String fullName, String shortName)
    {
        this.identifier = identifier;
        this.bookNumber = bookNumber;
        this.fullName = fullName;
        this.shortName = shortName;
        this.encountered = false;
    }
    
    public String GetIdentifier()
    {
        return this.identifier;
    }
    
    public Integer GetBookNumber()
    {
        return this.bookNumber;
    }
    
    public String GetFullName()
    {
        return this.fullName;
    }
    
    public String GetShortName()
    {
        return this.shortName;
    }
    
    public boolean GetEncountered()
    {
        return this.encountered;
    }
    
    public void SetEncountered(boolean encountered)
    {
        this.encountered = encountered;
    }
    
    protected String identifier;
    protected Integer bookNumber;
    protected String fullName;
    protected String shortName;
    protected boolean encountered;
}

class FootnoteMarkInfo
{
    public FootnoteMarkInfo(int startPos, String footnoteMark, int endPos)
    {
        this.startPos = startPos;
        this.footnoteMark = footnoteMark;
        this.endPos = endPos;
    }

    public int GetStartPos()
    {
        return this.startPos;
    }
    
    public String GetFootnoteMark()
    {
        return this.footnoteMark;
    }
    
    public int GetEndPos()
    {
        return this.endPos;
    }
    
    protected int startPos;
    protected String footnoteMark;
    protected int endPos;
}
