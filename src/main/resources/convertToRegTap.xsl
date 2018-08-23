<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rt="http://www.ivoa.net/xml/RegTAP/v1.0"
   xmlns:ri="http://www.ivoa.net/xml/RegistryInterface/v1.0" xmlns:vr="http://www.ivoa.net/xml/VOResource/v1.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:vs="http://www.ivoa.net/xml/VODataService/v1.1"
   xmlns:sia="http://www.ivoa.net/xml/SIA/v1.1" xmlns:stc="http://www.ivoa.net/xml/STC/stc-v1.30.xsd"
   xmlns:xlink="http://www.w3.org/1999/xlink" exclude-result-prefixes="#all"
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
         <xsl:apply-templates select="/*/ri:Resource" />
      </xsl:element>
   </xsl:template>
   <xsl:template match="ri:Resource">
      <xsl:element name="resource">
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
         <validationList>
            <xsl:apply-templates select=".//validationLevel"/>
         </validationList>
         <xsl:apply-templates/>
      </xsl:element>
   </xsl:template>
   
   <xsl:template match="validationLevel">
      <validation>
    <xsl:choose>
      <xsl:when test="parent::ri:Resource">
      <cap_index>-1</cap_index>
      </xsl:when>
      <xsl:otherwise>
      <cap_index><xsl:value-of select="count(preceding-sibling::validationLevel)"/></cap_index>
      </xsl:otherwise>
   </xsl:choose>
      <ivoid><xsl:value-of select="ancestor::ri:Resource/identifier"/></ivoid>
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
   <!-- copy everything else without nqmespace declarations... -->
   <xsl:template match="*">
      <xsl:element name="{name(.)}" namespace="{namespace-uri()}">
         <xsl:apply-templates select="@*" />
         <xsl:apply-templates />
      </xsl:element>
   </xsl:template>
   <xsl:template match="@*">
      <xsl:copy>
      </xsl:copy>
   </xsl:template>
</xsl:stylesheet>