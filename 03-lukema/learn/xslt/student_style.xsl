<xsl:stylesheet version="1.0" xmlns:xsl="https://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <xsl:for-each select="student">
      <xsl:value-of select="fname" /> - <xsl:value-of select="lname"></xsl:value-of>
    </xsl:for-each>
  </xsl:template>

  <xsl:for-each select="/student">
    <xsl:value-of select="fname" /> - <xsl:value-of select="lname"></xsl:value-of>
  </xsl:for-each>

</xsl:stylesheet>
