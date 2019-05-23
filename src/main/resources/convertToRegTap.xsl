<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rt="http://www.ivoa.net/xml/RegTAP/v1.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"    
   xmlns:ri="http://www.ivoa.net/xml/RegistryInterface/v1.0" xmlns:vr="http://www.ivoa.net/xml/VOResource/v1.0"
   xmlns:vs="http://www.ivoa.net/xml/VODataService/v1.1"
   xmlns:sia="http://www.ivoa.net/xml/SIA/v1.1" xmlns:stc="http://www.ivoa.net/xml/STC/stc-v1.30.xsd"
   xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="#all"
   xmlns="http://www.ivoa.net/xml/RegTAP/v1.0"
   xmlns:l="http://local" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
>
   <!-- change so that namespace not so important -->
   <xsl:output exclude-result-prefixes="#all" indent="yes"
      method="xml"
   >
   </xsl:output>
   <xsl:template match="/">
      <xsl:element name="regtap"
         namespace="http://www.ivoa.net/xml/RegTAP/v1.0"
      >
      <xsl:attribute name="schemaLocation" namespace="http://www.w3.org/2001/XMLSchema-instance">http://www.ivoa.net/xml/RegTAP/v1.0 ./RegTAP.xsd</xsl:attribute>
         <xsl:apply-templates select="/*/ri:Resource" />
      </xsl:element>
   </xsl:template>
   <xsl:template match="ri:Resource">
      <xsl:element name="resource">
         <ivoid><xsl:value-of select="identifier" /></ivoid>
         <altids>
            <xsl:apply-templates select="altIdentifier" />
         </altids>
         <xsl:apply-templates select=".//ri:Capability"/>
         <res_type>
            <xsl:value-of select="@xsi:type" />
         </res_type>
         <created>
            <xsl:value-of select="@created" />
         </created>
         <updated>
            <xsl:value-of select="@updated" />
         </updated>
         <status>
            <xsl:value-of select="@status" />
         </status>
         <short_name>
             <xsl:value-of select="shortName" />
         </short_name>
         <res_title>
             <xsl:value-of select="title"/>
         </res_title>
         <content_level>
            <xsl:value-of select="content/contentLevel" separator="#"/>
         </content_level>
         <res_description>
            <xsl:value-of select="content/description"/>
         </res_description>
         <reference_url>
            <xsl:value-of select="content/referenceURL"/>
         </reference_url>
         <creator_seq>
           <xsl:value-of select="curation/creator/name"/>
         </creator_seq>
         <content_type>
           <xsl:value-of select="content/type" separator="#"/>
         </content_type>
         <source_format>
           <xsl:value-of select="content/source/@format"/>
         </source_format>
         <source_value>
           <xsl:value-of select="content/source"/>
         </source_value>
         <res_version>
            <xsl:value-of select="curation/version"/>
         </res_version>
        
          <xsl:apply-templates select="coverage/regionOfRegard"/>
         
         <waveband>
             <xsl:value-of select="coverage/waveband" separator="#"/>
         </waveband>
         <rights>
            <xsl:value-of select="rights"/>
         </rights>
         <rights_uri>
            <xsl:value-of select="rights/@rightsURI"/>
         </rights_uri>
         <roles>
             <xsl:apply-templates select="curation/(contact|publisher|creator|contibutor)"/>
         </roles>
         <subjects>
            <xsl:apply-templates select="content/subject"/>
         </subjects>
         <capabilities>
            <xsl:apply-templates select="capability"/>
         </capabilities>
         <validationList>
            <xsl:apply-templates select=".//validationLevel"/>
         </validationList>
         <relationships>
               <xsl:apply-templates select="content/relationship"/>
         </relationships>
         <dates>
              <xsl:apply-templates select="curation/date"/>
         </dates>
         <schemata>
            <xsl:apply-templates select="tableset/schema"/>
         </schemata>
         <tables>
             <xsl:apply-templates select="tableset/schema/tables"/>
         </tables>
         
         <details>
            <xsl:call-template name="details"/>
         </details>
      </xsl:element>
   </xsl:template>
   <xsl:template match="date"><!-- FIXME still need to think about the exact date formatting -->
   <date>
      <xsl:call-template name="id"/>
      <value_role><xsl:value-of select="@role"/></value_role>
      <date_value>
        <xsl:choose>
          <xsl:when test="string-length(.) lt 12">
          <xsl:value-of select="concat(.,'T00:00:00')"/>
          </xsl:when>
          <xsl:otherwise>
          <xsl:value-of select="."/>
          </xsl:otherwise>
        </xsl:choose>
      
      </date_value>
   </date>
   </xsl:template>
   <xsl:template match="altIdentifier">
     <alt_identifier>
          <xsl:call-template name="id"/>
          <id><xsl:value-of select ="."/></id>
     </alt_identifier>
   </xsl:template>
   <xsl:template match="regionOfRegard">
   <region_of_regard>
     <xsl:value-of select="."/>
   </region_of_regard>
   </xsl:template>
   
   <xsl:template match="relationship">
     <xsl:for-each select="relatedResource">
       <relationship>
         <xsl:call-template name="id"/>
         <relationship_type><!-- FIXME need to map these to new vocab -->
           <xsl:value-of select="../relationshipType"/>
         </relationship_type>
         <related_id><xsl:value-of select="../relatedResource/@ivo-id"/></related_id>
         <related_name><xsl:value-of select="../relatedResource"/></related_name>
       </relationship>
     </xsl:for-each>
   </xsl:template>
   
   
   
    <xsl:template match="curation/contact|curation/creator">
    <role>
      <xsl:call-template name="id"/>
       <role_ivoid>
         <xsl:value-of select="name/@ivo-id"/>
       </role_ivoid>
       <base_role><xsl:value-of select ="local-name()"/></base_role>
       <role_name>
         <xsl:value-of select="name"/>
       </role_name>
       <xsl:apply-templates select="node() except name"/>
    </role>
    </xsl:template>
    <xsl:template match="curation/publisher|curation/contributor">
     <role>
      <xsl:call-template name="id"/>
       <role_ivoid>
         <xsl:value-of select="@ivo-id"/>
       </role_ivoid>
       <base_role><xsl:value-of select ="local-name()"/></base_role>
       <role_name>
         <xsl:value-of select="."/>
       </role_name>
    </role>
    
    </xsl:template>
    
     <xsl:template match="subject">
       <subject>
         <xsl:call-template name="id"/>
          <subject><xsl:value-of select ="."/></subject>
       </subject>
     </xsl:template>
    
    <xsl:template match="capability">
    <capability>
     <xsl:call-template name="id"/>
     <xsl:call-template name="capidx"/>     
     <cap_type><xsl:value-of select="@xsi:type"/></cap_type>
     <cap_description><xsl:value-of select="description"/></cap_description>
     <standard_id><xsl:value-of select="@standardID"/></standard_id>
     <xsl:apply-templates select="interface" />
     </capability>
    </xsl:template>
    
   <xsl:template match="interface">
      <interface>
         <xsl:call-template name="id"/>
         <intf_index><xsl:value-of select="count(preceding::interface[ancestor::ri:Resource/identifier=current()/ancestor::ri:Resource/identifier])+1"/></intf_index>
         <xsl:call-template name="capidx"/>     
         <intf_type><xsl:value-of select="@xsi:type"/></intf_type>
         <intf_role><xsl:value-of select="@role"/></intf_role>
         <std_version><xsl:value-of select="@version"/></std_version>
         <query_type><xsl:value-of select="queryType" separator="#"/></query_type>
         <result_type><xsl:value-of select="resultType"/></result_type>
         <xsl:apply-templates select="wsdlURL" />
         <url_use><xsl:value-of select="accessURL[1]/@use"/></url_use>
         <access_url><xsl:value-of select="accessURL[1]"/></access_url>
         <mirror_url><xsl:value-of select="accessURL[position()!= 1]|mirrorURL" separator="#"/></mirror_url>
         <authenticated_only>
            <xsl:choose>
              <xsl:when test="securityMethod">1</xsl:when>
              <xsl:otherwise>0</xsl:otherwise>
            </xsl:choose>
         </authenticated_only>
         <xsl:apply-templates select="param" />
      </interface>
   </xsl:template>
    <xsl:template match="param">
      <param>
          <xsl:call-template name="id"/>
          <intf_index><xsl:value-of select="count(preceding::interface[ancestor::ri:Resource/identifier=current()/ancestor::ri:Resource/identifier])+1"/></intf_index>
          <xsl:apply-templates select="node()" />
          <xsl:apply-templates select="@*" />
          
      </param>
    </xsl:template>
    
    <xsl:template match="@std">
      <std>
        <xsl:choose>
         <xsl:when test=".=true()">1</xsl:when>
         <xsl:otherwise>0</xsl:otherwise>
        </xsl:choose>
      </std>
    </xsl:template>
   
   <xsl:template match="validationLevel">
      <validation>
       <xsl:call-template name="id"/>
       <xsl:call-template name="capidx"/>
      <validated_by><xsl:value-of select="@validatedBy"/></validated_by>
      <level><xsl:value-of select="."/></level>
      
      </validation>
   </xsl:template>
   
   <!-- simple name substitutions -->
   <xsl:template match="identifier">
      <xsl:element name="ivorn">
         <xsl:value-of select="." />
      </xsl:element>
   </xsl:template>
   <xsl:template match="shortName">
      <short_name>
         <xsl:value-of select="." />
      </short_name>
   </xsl:template>
   
    
   <xsl:template match="schema">
     <schema>
      <xsl:call-template name="id"/>
      <schema_index><xsl:value-of select="count(preceding-sibling::schema)+1" /></schema_index>
      <xsl:apply-templates select="node() except table" />
    </schema>  
   </xsl:template>
    <xsl:template match="table">
    <table>
       <xsl:call-template name="id"/>
       <table_index><xsl:value-of select="count(preceding::table[ancestor::ri:Resource/identifier=current()/ancestor::ri:Resource/identifier])+1"/></table_index>
       <schema_index><xsl:value-of select="count(../preceding-sibling::schema)+1"/></schema_index>
       <xsl:apply-templates select="node() except foreignKey" />
    </table>
    </xsl:template>
    <xsl:template match="column">
    <column>
       <xsl:call-template name="id"/>
       <table_index><xsl:value-of select="count(preceding::table[ancestor::ri:Resource/identifier=current()/ancestor::ri:Resource/identifier])+1"/></table_index>
       <xsl:apply-templates select="name" />
       <xsl:call-template name="bool"><xsl:with-param name="att">std</xsl:with-param></xsl:call-template>
       <flag><xsl:value-of select="flag" separator="#"/></flag>
       <xsl:apply-templates select="node() except (name|flag)"/>  
    </column>
    </xsl:template>
    
    <xsl:template match="dataType">
       <datatype><xsl:value-of select="."/></datatype>
       <extended_schema><xsl:value-of select="@extendedSchema"/></extended_schema>
       <extended_type><xsl:value-of select="@extendedType"/></extended_type>
       <arraysize><xsl:value-of select="@arraysize"/></arraysize>
       <delim><xsl:value-of select="@delim"/></delim>
       <xsl:if test="parent::column">
       <type_system><xsl:value-of select="@xsi:type"/></type_system>
       </xsl:if>
    </xsl:template>
    
   <!--  -->
   <xsl:template name="id">
      <ivoid><xsl:value-of select="ancestor::ri:Resource/identifier"/></ivoid>
   </xsl:template>
    <xsl:template name="capidx"> 
    <xsl:choose>
      <xsl:when test="ancestor-or-self::capability">
        <cap_index><xsl:value-of select="count(parent::capability/preceding-sibling::capability)+1"/></cap_index>
      </xsl:when>
      <xsl:otherwise>
        <xsl:element name="cap_index"><xsl:attribute name="nil" namespace="http://www.w3.org/2001/XMLSchema-instance">true</xsl:attribute></xsl:element>
      </xsl:otherwise>
   </xsl:choose>
    
   </xsl:template>
   
   <xsl:template name="bool">
   <xsl:param name="att"/>
    <xsl:element name="{$att}">
     <xsl:choose>
       <xsl:when test="@*[name()=$att]">
       <xsl:choose>
          <xsl:when test="@*[name()=$att]=true()">1</xsl:when>
          <xsl:otherwise>0</xsl:otherwise>
       </xsl:choose>      
       </xsl:when>      
       <xsl:otherwise>
         <xsl:attribute name="nil" namespace="http://www.w3.org/2001/XMLSchema-instance">true</xsl:attribute>
       </xsl:otherwise>
     </xsl:choose>
    </xsl:element>
   </xsl:template>


    <xsl:template name="details">
 
 <!-- list of xpaths from the std document appendix - 
  might be better to do via exclusions or fewer more general xpaths -->  
       <xsl:apply-templates mode="detail" select="
accessURL|
capability/executionDuration/hard|
capability/complianceLevel|
capability/creationType|
capability/dataModel|
capability/dataModel/@ivo-id|
capability/dataSource|
capability/defaultMaxRecords|
capability/executionDuration/default|
capability/imageServiceType|
capability/interface/securityMethod/@standardID|
capability/interface/testQueryString|
capability/language/name|
capability/language/version/@ivo-id|
capability/maxAperture|
capability/maxFileSize|
capability/maxImageExtent/lat|
capability/maxImageExtent/long|
capability/maxImageSize/lat|
capability/maxImageSize/long|
capability/maxImageSize|
capability/maxQueryRegionSize/lat|
capability/maxQueryRegionSize/long|
capability/maxRecords|
capability/maxSearchRadius|
capability/maxSR|
capability/outputFormat/@ivo-id|
capability/outputFormat/alias|
capability/outputFormat/mime|
capability/outputLimit/default|
capability/outputLimit/default/@unit|
capability/outputLimit/hard|
capability/outputLimit/hard/@unit|
capability/retentionPeriod/default|
capability/retentionPeriod/hard|
capability/supportedFrame|
capability/testQuery/catalog|
capability/testQuery/dec|
capability/testQuery/extras|
capability/testQuery/pos/lat|
capability/testQuery/pos/long|
capability/testQuery/pos/refframe|
capability/testQuery/queryDataCmd|
capability/testQuery/ra|
capability/testQuery/size|
capability/testQuery/size/lat|
capability/testQuery/size/long|
capability/testQuery/sr|
capability/testQuery/verb|
capability/uploadLimit/default|
capability/uploadLimit/default/@unit|
capability/uploadLimit/hard|
capability/uploadLimit/hard/@unit|
capability/uploadMethod/@ivo-id|
capability/verbosity|
coverage/footprint|
coverage/footprint/@ivo-id|
deprecated|
endorsedVersion|
facility|
format|
format/@isMIMEType|
full|
instrument|
instrument/@ivo-id|
managedAuthority|
managingOrg|
rights|
rights/@rightsURI|
schema/@namespace" />
     
    </xsl:template>
  
  <xsl:template match="*|@*" mode="detail">
  <detail>
     <xsl:call-template name="id"/>
     <xsl:call-template name="capidx"/>
     <detail_utype><xsl:value-of select="l:generateXPath(current())"/></detail_utype>
     <detail_value><xsl:value-of select="."/></detail_value>
     
     
  </detail>
  </xsl:template>
  
   <!-- copy everything else without nqmespace declarations... -->
   <xsl:template match="*">
      <xsl:element name="{name(.)}" namespace="http://www.ivoa.net/xml/RegTAP/v1.0">
         <xsl:apply-templates select="@*" />
         <xsl:apply-templates />
      </xsl:element>
   </xsl:template>
   <xsl:template match="@*"> <!-- turn into element -->
       <xsl:element name="{name(.)}" namespace="http://www.ivoa.net/xml/RegTAP/v1.0">
         <xsl:value-of select="."/>
      </xsl:element>
      
   </xsl:template>
<xsl:function name="l:generateXPath" as="xsd:string" >
  <xsl:param name="pNode" as="node()"/>
  <xsl:variable name="head" >
  <xsl:value-of select="$pNode/ancestor::*[local-name() !=  'Resource' and local-name() !=  'VOResources' ]/name()" separator="/" />
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="$pNode instance of element()"><xsl:value-of select="($head, name($pNode))" separator="/"/></xsl:when>
    <xsl:otherwise><xsl:value-of select="($head, name($pNode))" separator="@"/></xsl:otherwise>
  </xsl:choose>
</xsl:function>    
</xsl:stylesheet>