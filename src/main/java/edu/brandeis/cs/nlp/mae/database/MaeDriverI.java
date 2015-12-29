/*
 * MAE - Multi-purpose Annotation Environment
 *
 * Copyright Keigh Rim (krim@brandeis.edu)
 * Department of Computer Science, Brandeis University
 * Original program by Amber Stubbs (astubbs@cs.brandeis.edu)
 *
 * MAE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, @see <a href="http://www.gnu.org/licenses">http://www.gnu.org/licenses</a>.
 *
 * For feedback, reporting bugs, use the project repo on github
 * @see <a href="https://github.com/keighrim/mae-annotation">https://github.com/keighrim/mae-annotation</a>
 */

package edu.brandeis.cs.nlp.mae.database;

import com.j256.ormlite.support.ConnectionSource;
import edu.brandeis.cs.nlp.mae.io.MaeIODTDException;
import edu.brandeis.cs.nlp.mae.io.MaeIOXMLException;
import edu.brandeis.cs.nlp.mae.model.*;
import edu.brandeis.cs.nlp.mae.util.HashedSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created by krim on 12/27/2015.
 */
public interface MaeDriverI {

    void setupDatabase(ConnectionSource source) throws MaeDBException;

    void destroy() throws MaeDBException;

    // task
    void readTask(File file) throws MaeDBException, MaeIODTDException, FileNotFoundException;

    String getTaskName() throws MaeDBException;

    void setTaskName(String value) throws MaeDBException;

    void readAnnotation(File file) throws MaeDBException, MaeIOXMLException, FileNotFoundException;

    String getTaskFileName() throws MaeDBException;

    void setTaskFileName(String fileName) throws MaeDBException;

    String getAnnotationFileName() throws MaeDBException;

    void setAnnotationFileName(String fileName) throws MaeDBException;

    String getPrimaryText() throws MaeDBException;

    void setPrimaryText(String text) throws MaeDBException;

    // tag types
    TagType createTagType(String name, String prefix, boolean isLink) throws MaeDBException;

    List<TagType> getAllTagTypes() throws MaeDBException;

    List<TagType> getExtentTagTypes() throws MaeDBException;

    List<TagType> getLinkTagTypes() throws MaeDBException;

    TagType getTagTypeByName(String name) throws MaeDBException;

    boolean setTagTypePrefix(TagType tagType, String prefix) throws MaeDBException;

    boolean setTagTypeNonConsuming(TagType tagType, boolean b) throws MaeDBException;

    // tag common
    Tag getTagByTid(String tid) throws MaeDBException;

    // extent tags
    ExtentTag createExtentTag(String tid, TagType tagType, String text, int[] spans) throws MaeDBException;
            // create one using existing tid (e.g. reading from annotation xml)

    ExtentTag createExtentTag(TagType tagType, String text, int[] spans) throws MaeDBException;
            // create one with autogenerated tid (need auto generation inside driver impl)

    HashedSet<TagType,ExtentTag> getTagsByTypesAt(int location) throws MaeDBException;

    HashedSet<TagType,ExtentTag> getTagsByTypesIn(int[] locations) throws MaeDBException;

    HashedSet<TagType,ExtentTag> getTagsByTypesBetween(int begin, int end) throws MaeDBException;

    List<ExtentTag> getAllExtentTagsOfType(TagType type) throws MaeDBException, IllegalArgumentException;

    // link tags
    LinkTag createLinkTag(String tid, TagType tagType) throws MaeDBException;

    LinkTag createLinkTag(TagType tagType) throws MaeDBException;

    List<LinkTag> getAllLinkTagsOfType(TagType type) throws MaeDBException, IllegalArgumentException;

    // att types
    AttributeType createAttributeType(TagType linktag, String from) throws MaeDBException;

    List<AttributeType> getAttributeTypesOfTagType(TagType type) throws MaeDBException;

    void setAttributeTypeValueSet(AttributeType type, List<String> validValues) throws MaeDBException;

    void setAttributeTypeDefaultValue(AttributeType type, String defaultValue) throws MaeDBException;

    void setAttributeTypeIDRef(AttributeType type, boolean b) throws MaeDBException;

    void setAttributeTypeRequired(AttributeType type) throws MaeDBException;

    // atts
    Attribute addAttribute(Tag tag, AttributeType attType, String attValue) throws MaeDBException;

    Map<String, String> getAttributeMapOfTag(Tag tag) throws MaeDBException;

    // arg types
    ArgumentType createArgumentType(TagType linktag, String from) throws MaeDBException;

    List<ArgumentType> getArgumentTypesOfLinkTagType(TagType link) throws MaeDBException;

    void setArgumentTypeRequired(ArgumentType type) throws MaeDBException;

    // args
    Argument addArgument(LinkTag linker, ArgumentType argType, ExtentTag argument) throws MaeDBException;
}
