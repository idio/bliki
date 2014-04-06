package info.bliki.wiki.filter;

import info.bliki.wiki.model.BBCodeModel;
import info.bliki.wiki.model.WikiModel;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class BBCodeFilterTest extends FilterTestSupport
{

    @Override
    protected WikiModel newWikiTestModel(Locale locale) {
        WikiModel wikiModel = new BBCodeModel("http://en.wikipedia.org/wiki/Image:${image}",
                "http://en.wikipedia.org/wiki/${title}");
        wikiModel.setUp();
        return wikiModel;
    }

    @Test public void testbb1()
    {
        assertEquals("\n" +
                "<p>This is a <i>simple</i> paragraph.</p>", wikiModel.render("This is a [i]simple[/i] paragraph.", false));
    }

    @Test public void testbb2()
    {
        assertEquals("\n" +
                "<p>This is a <font color=\"red\">simple</font> paragraph.</p>", wikiModel.render(
                "This is a [color=red]simple[/color] paragraph.", false));
    }

    @Test public void testbb3()
    {
        assertEquals("\n" +
                "<p>\n" +
                "<ul>\n" +
                "\n" +
                "<li><b>Red</b> and <i>Green</i>\n" +
                "</li>\n" +
                "<li>Blue\n" +
                "</li>\n" +
                "<li>Yellow\n" +
                "</li></ul></p>", wikiModel.render(
                "[list]\n[*][b]Red[/b] and [i]Green[/i]\n[*]Blue\n[*]Yellow\n[/list]", false));
    }

    @Test public void testbb4()
    {
        assertEquals("\n" +
                "<p>\n" +
                "<ul>\n" +
                "Red\n" +
                "Blue\n" +
                "Yellow\n" +
                "</ul></p>", wikiModel.render("[list]\nRed\nBlue\nYellow\n[/list]", false));
    }

    @Test public void testbb5()
    {
        assertEquals("\n" +
                "<p>\n" +
                "<pre class=\"code\">\n" +
                "A code block \n" +
                "</pre></p>", wikiModel.render(
                "[code]\nA code block \n[/code]", false));
    }

    @Test public void testbb6()
    {
        assertEquals("\n" +
                "<p><a href=\"http://www.example.com\">http://www.example.com\n" +
                "</a></p>", wikiModel.render(
                "[url]http://www.example.com\n[/url]", false));
    }

    @Test public void testbb7()
    {
        assertEquals("\n" +
                "<p><a href=\"http://www.example.com\">Example Site</a></p>", wikiModel.render(
                "[url=http://www.example.com]Example Site[/url]", false));
    }

    @Test public void testbb8() {
        assertEquals("\n" +
                "<p><blockquote>start blockquote here\n" +
                "\n" +
                "line above me\n" +
                "no line above me &#60;&#34;&#62; and i am <b>bold</b>\n" +
                "\n" +
                "and line above me\n" +
                "end of blockquote here</blockquote> </p>", wikiModel.render("[quote]start blockquote here\n" +
                "\n" +
                "line above me\n" +
                "no line above me <\"> and i am [b]bold[/b]\n" +
                "\n" +
                "and line above me\n" +
                "end of blockquote here[/quote] ", false));
    }

    @Test public void testbb9()
    {
        assertEquals("\n" +
                "<p>\n" +
                "<ul>\n" +
                "\n" +
                "<li><blockquote><b>Red</b> and <i>Green</i></blockquote> colors\n" +
                "</li>\n" +
                "<li>Blue\n" +
                "</li>\n" +
                "<li>Yellow\n" +
                "</li></ul></p>", wikiModel.render(
                "[list]\n[*][quote][b]Red[/b] and [i]Green[/i][/quote] colors\n[*]Blue\n[*]Yellow\n[/list]", false));
    }

}