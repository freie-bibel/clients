/* Copyright (C) 2015  Stephan Kreutzer
 *
 * This file is part of sermon_online_vpl2haggai1 workflow.
 *
 * sermon_online_vpl2haggai1 workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3 or any later version,
 * as published by the Free Software Foundation.
 *
 * sermon_online_vpl2haggai1 workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License 3 for more details.
 *
 * You should have received a copy of the GNU Affero General Public License 3
 * along with sermon_online_vpl2haggai1 workflow. If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @file $/sermon_online_vpl2haggai1 workflow.java
 * @author Stephan Kreutzer
 * @since 2015-05-12
 */



import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;



public class sermon_online_vpl2haggai1
{
    public static void main(String args[])
    {
        System.out.print("sermon_online_vpl2haggai1 workflow  Copyright (C) 2015  Stephan Kreutzer\n" +
                         "This program comes with ABSOLUTELY NO WARRANTY.\n" +
                         "This is free software, and you are welcome to redistribute it\n" +
                         "under certain conditions. See the GNU Affero General Public\n" +
                         "License 3 or any later version for details. Also, see the source code\n" +
                         "repository https://github.com/free-scriptures/clients/\n" +
                         "and the project website http://www.free-scriptures.org.\n\n");

        String programPath = sermon_online_vpl2haggai1.class.getProtectionDomain().getCodeSource().getLocation().getFile();


        File vplFile = null;

        if (args.length >= 1)
        {
            vplFile = new File(args[0]);
        }
        else
        {
            ProcessBuilder builder = new ProcessBuilder("java", "file_picker1", programPath + "config_file_picker1_in.xml");
            builder.directory(new File(programPath + ".." + File.separator + ".." + File.separator +  ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "gui" + File.separator + "file_picker" + File.separator + "file_picker1"));
            builder.redirectErrorStream(true);

            try
            {
                Process process = builder.start();
                Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");
                
                while (scanner.hasNext() == true)
                {
                    String line = scanner.next();
                    
                    System.out.println(line);
                    
                    if (line.contains("' selected.") == true)
                    {
                        StringTokenizer tokenizer = new StringTokenizer(line, "'");
                        
                        if (tokenizer.countTokens() >= 2)
                        {
                            tokenizer.nextToken();
                            vplFile = new File(tokenizer.nextToken());
                        }
                    }
                }
                
                scanner.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        if (vplFile == null)
        {
            System.out.println("sermon_online_vpl2haggai1 workflow: No input VPL file specified.");
            System.exit(-1);
        }

        if (vplFile.exists() != true)
        {
            System.out.print("sermon_online_vpl2haggai1 workflow: VPL file '" + vplFile.getAbsolutePath() + "' doesn't exist.\n");
            System.exit(-1);
        }

        if (vplFile.isFile() != true)
        {
            System.out.print("sermon_online_vpl2haggai1 workflow: Path '" + vplFile.getAbsolutePath() + "' isn't a file.\n");
            System.exit(-1);
        }

        if (vplFile.canRead() != true)
        {
            System.out.print("sermon_online_vpl2haggai1 workflow: VPL file '" + vplFile.getAbsolutePath() + "' isn't readable.\n");
            System.exit(-1);
        }


        File jobFile = null;

        if (args.length >= 2)
        {
            jobFile = new File(args[1]);
        }
        else
        {
            ProcessBuilder builder = new ProcessBuilder("java", "file_picker1", programPath + "config_file_picker1_jobfile.xml");
            builder.directory(new File(programPath + ".." + File.separator + ".." + File.separator +  ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "gui" + File.separator + "file_picker" + File.separator + "file_picker1"));
            builder.redirectErrorStream(true);

            try
            {
                Process process = builder.start();
                Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");
                
                while (scanner.hasNext() == true)
                {
                    String line = scanner.next();
                    
                    System.out.println(line);
                    
                    if (line.contains("' selected.") == true)
                    {
                        StringTokenizer tokenizer = new StringTokenizer(line, "'");
                        
                        if (tokenizer.countTokens() >= 2)
                        {
                            tokenizer.nextToken();
                            jobFile = new File(tokenizer.nextToken());
                        }
                    }
                }
                
                scanner.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        if (jobFile == null)
        {
            System.out.println("sermon_online_vpl2haggai1 workflow: No job file specified.");
            System.exit(-1);
        }

        if (jobFile.exists() != true)
        {
            System.out.print("sermon_online_vpl2haggai1 workflow: Job file '" + jobFile.getAbsolutePath() + "' doesn't exist.\n");
            System.exit(-1);
        }

        if (jobFile.isFile() != true)
        {
            System.out.print("sermon_online_vpl2haggai1 workflow: Path '" + jobFile.getAbsolutePath() + "' isn't a file.\n");
            System.exit(-1);
        }

        if (jobFile.canRead() != true)
        {
            System.out.print("sermon_online_vpl2haggai1 workflow: Job file '" + jobFile.getAbsolutePath() + "' isn't readable.\n");
            System.exit(-1);
        }

        File tempDirectory = new File(programPath + "temp");

        if (tempDirectory.exists() != true)
        {
            if (tempDirectory.mkdir() != true)
            {
                System.out.print("sermon_online_vpl2haggai1 workflow: Can't create temp directory '" + tempDirectory.getAbsolutePath() + "'.\n");
                System.exit(-1);
            }
        }
        else
        {
            if (tempDirectory.isDirectory() != true)
            {
                System.out.print("sermon_online_vpl2haggai1 workflow: Temp directory path '" + tempDirectory.getAbsolutePath() + "' exists, but isn't a directory.\n");
                System.exit(-1);
            }
        }


        ProcessBuilder builder = new ProcessBuilder("java", "txtreplace1", vplFile.getAbsolutePath(), programPath + "txtreplace1_replacement_dictionary.xml", tempDirectory.getAbsolutePath() + File.separator + "prepared.txt");
        builder.directory(new File(programPath + ".." + File.separator + ".." + File.separator +  ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "txtreplace" + File.separator + "txtreplace1"));
        builder.redirectErrorStream(true);

        try
        {
            Process process = builder.start();
            Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");

            while (scanner.hasNext() == true)
            {
                String line = scanner.next();

                System.out.println(line);
            }
            
            scanner.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }

        File resultFile = new File(tempDirectory.getAbsolutePath() + File.separator + "result.xml");

        builder = new ProcessBuilder("java", "sermon_online_vpl2haggai1", tempDirectory.getAbsolutePath() + File.separator + "prepared.txt", jobFile.getAbsolutePath(), resultFile.getAbsolutePath());
        builder.directory(new File(programPath + ".." + File.separator + ".." + File.separator + "sermon_online_vpl2haggai1"));
        builder.redirectErrorStream(true);

        try
        {
            Process process = builder.start();
            Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");

            while (scanner.hasNext() == true)
            {
                String line = scanner.next();

                System.out.println(line);
            }
            
            scanner.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }

        boolean resultValid = false;

        builder = new ProcessBuilder("java", "schemavalidator1", tempDirectory.getAbsolutePath() + File.separator + "result.xml", programPath + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "schemavalidator" + File.separator + "schemavalidator1" + File.separator + "entities" + File.separator + "config_empty.xml", programPath + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "resources" + File.separator + "free-scriptures.org" + File.separator + "haggai_20130620.xsd", programPath + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "resources" + File.separator + "free-scriptures.org" + File.separator + "config_schemata_haggai_20130620.xml");
        builder.directory(new File(programPath + ".." + File.separator + ".." + File.separator + ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "schemavalidator" + File.separator + "schemavalidator1"));
        builder.redirectErrorStream(true);

        try
        {
            Process process = builder.start();
            Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");

            while (scanner.hasNext() == true)
            {
                String line = scanner.next();

                System.out.println(line);
                
                if (line.contains("schemavalidator1: Valid.") == true)
                {
                    resultValid = true;
                }
            }
            
            scanner.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
        
        if (resultValid != true)
        {
            System.exit(-1);
        }
        
        File outFile = null;

        if (args.length >= 3)
        {
            outFile = new File(args[2]);
        }
        else
        {
            builder = new ProcessBuilder("java", "file_picker1", programPath + "config_file_picker1_out.xml");
            builder.directory(new File(programPath + ".." + File.separator + ".." + File.separator +  ".." + File.separator + ".." + File.separator + "free-scriptures" + File.separator + "tools" + File.separator + "gui" + File.separator + "file_picker" + File.separator + "file_picker1"));
            builder.redirectErrorStream(true);

            try
            {
                Process process = builder.start();
                Scanner scanner = new Scanner(process.getInputStream()).useDelimiter("\n");
                
                while (scanner.hasNext() == true)
                {
                    String line = scanner.next();
                    
                    System.out.println(line);
                    
                    if (line.contains("' selected.") == true)
                    {
                        StringTokenizer tokenizer = new StringTokenizer(line, "'");
                        
                        if (tokenizer.countTokens() >= 2)
                        {
                            tokenizer.nextToken();
                            outFile = new File(tokenizer.nextToken());
                        }
                    }
                }
                
                scanner.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        if (outFile == null)
        {
            System.exit(0);
        }

        if (outFile.exists() == true)
        {
            if (outFile.isFile() != true)
            {
                System.out.print("sermon_online_vpl2haggai1 workflow: Path '" + outFile.getAbsolutePath() + "' exists already, but isn't a file.\n");
                System.exit(-1);
            }
        }

        sermon_online_vpl2haggai1.CopyFileBinary(resultFile, outFile);
    }

    public static int CopyFileBinary(File from, File to)
    {
        if (from.exists() != true)
        {
            System.out.println("sermon_online_vpl2haggai1 workflow: Can't copy '" + from.getAbsolutePath() + "' to '" + to.getAbsolutePath() + "' because '" + from.getAbsolutePath() + "' doesn't exist.");
            return -1;
        }
        
        if (from.isFile() != true)
        {
            System.out.println("sermon_online_vpl2haggai1 workflow: Can't copy '" + from.getAbsolutePath() + "' to '" + to.getAbsolutePath() + "' because '" + from.getAbsolutePath() + "' isn't a file.");
            return -2;
        }
        
        if (from.canRead() != true)
        {
            System.out.println("sermon_online_vpl2haggai1 workflow: Can't copy '" + from.getAbsolutePath() + "' to '" + to.getAbsolutePath() + "' because '" + from.getAbsolutePath() + "' isn't readable.");
            return -3;
        }
    
    
        byte[] buffer = new byte[1024];

        try
        {
            to.createNewFile();

            FileInputStream reader = new FileInputStream(from);
            FileOutputStream writer = new FileOutputStream(to);
            
            int bytesRead = reader.read(buffer, 0, buffer.length);
            
            while (bytesRead > 0)
            {
                writer.write(buffer, 0, bytesRead);
                bytesRead = reader.read(buffer, 0, buffer.length);
            }
            
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
    
        return 0;
    }
}

