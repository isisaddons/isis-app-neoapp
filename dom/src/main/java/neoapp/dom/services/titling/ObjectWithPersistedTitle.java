package neoapp.dom.services.titling;

import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

/**
 * Entities implementing this interface will surface a title that can be used by Neo4J as the label when inspecting the graph of persisted nodes.
 */
public interface ObjectWithPersistedTitle {

    /**
     * This property should <i>not</i> be annotated or otherwise used as part of the object's title;
     * rather it is derived from and holds the object's title.
     */
    @PropertyLayout(hidden = Where.EVERYWHERE)
    String getTitle();
    void setTitle(final String title);
}
