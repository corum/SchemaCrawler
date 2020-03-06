package schemacrawler.tools.integration.diagram;


import static schemacrawler.tools.integration.diagram.DiagramOutputFormat.plain;
import static schemacrawler.tools.integration.diagram.DiagramOutputFormat.png;
import static schemacrawler.tools.integration.diagram.DiagramOutputFormat.ps;
import static schemacrawler.tools.integration.diagram.DiagramOutputFormat.svg;
import static schemacrawler.tools.integration.diagram.DiagramOutputFormat.xdot;
import static sf.util.Utility.isClassAvailable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;

import sf.util.FileContents;
import sf.util.ProcessExecutor;
import sf.util.SchemaCrawlerLogger;
import sf.util.StringFormat;

public final class GraphvizUtility
{

  private static final SchemaCrawlerLogger LOGGER =
    SchemaCrawlerLogger.getLogger(GraphvizUtility.class.getName());

  public static boolean isGraphvizAvailable()
  {
    final List<String> command = new ArrayList<>();
    command.add("dot");
    command.add("-V");

    LOGGER.log(Level.INFO,
               new StringFormat("Checking if Graphviz is available:%n%s",
                                command.toString()));

    final ProcessExecutor processExecutor = new ProcessExecutor();
    processExecutor.setCommandLine(command);

    Integer exitCode;
    try
    {
      exitCode = processExecutor.call();
      LOGGER.log(Level.INFO,
                 new FileContents(processExecutor.getProcessOutput()));
    }
    catch (final Exception e)
    {
      LOGGER.log(Level.WARNING, "Could not execute Graphviz command", e);
      LOGGER.log(Level.WARNING,
                 new FileContents(processExecutor.getProcessError()));

      exitCode = Integer.MIN_VALUE;
    }
    final boolean successful = exitCode != null && exitCode == 0;

    return successful;
  }

  public static boolean isGraphvizJavaAvailable(final DiagramOutputFormat diagramOutputFormat)
  {
    final String className = "guru.nidi.graphviz.engine.Graphviz";
    final boolean hasClass = isClassAvailable(className);
    final boolean supportsFormat = EnumSet
      .of(svg, png, ps, xdot, plain)
      .contains(diagramOutputFormat);

    LOGGER.log(Level.INFO,
               new StringFormat("Checking if diagram can be generated - "
                                + " can load <%s> = <%b>, "
                                + " can generate format <%s> = <%b>",
                                className,
                                hasClass,
                                diagramOutputFormat.getDescription(),
                                supportsFormat));

    return hasClass && supportsFormat;
  }

  private GraphvizUtility()
  {
    // Prevent instantiation
  }

}
