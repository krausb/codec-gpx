/*
 * Copyright (C) 2018  Bastian Kraus
 *
 * This program is free software: you can redistribute it and/or modify
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.streamarchitect.codec.gpx

import io.streamarchitect.platform.model.GpxType
import org.apache.logging.log4j.LogManager
import org.scalatest.{ MustMatchers, WordSpecLike }

import scala.io.Source
import scala.xml.XML

/**
  * Test spec to verify that the decoding of GPX files is working.
  */
class GpxDecodeSpec extends WordSpecLike with MustMatchers {

  private val log = LogManager.getLogger(this.getClass)

  "A GPX XML should" should {

    "successful be parsed from a GPX file and mapped to a scala object hierarchy" in {

      scalaxb.fromXML[GpxType](XML.loadString(GpxDecodeSpec.loadXmlStringFromResource))

    }

  }

  "A GpxDecoder should" should {

    "successful decode a GPX XML file and return the filecontent as a GpxType object tree" in {

      GpxCodec.decode(GpxDecodeSpec.loadXmlStringFromResource)

    }

  }

  "A GpxType object tree should" should {

    "successful be encoded as a XML string" in {

      val xmlString   = GpxDecodeSpec.loadXmlStringFromResource
      val gpxTypeElem = scalaxb.fromXML[GpxType](XML.loadString(xmlString))
      GpxCodec.encode(gpxTypeElem)

    }

  }

}

object GpxDecodeSpec {

  private val log = LogManager.getLogger(this.getClass)

  private def loadXmlStringFromResource(): String =
    Source
      .fromInputStream(
        getClass.getResourceAsStream("/test_trace.gpx")
      )
      .getLines()
      .mkString

}
