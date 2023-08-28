package io.flywheel.sandbox.cli

import groovy.io.FileType
import groovy.transform.Field
import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import picocli.CommandLine.Command
import picocli.CommandLine.Parameters
import picocli.groovy.PicocliScript2

import java.nio.file.Paths

@Grapes([
        @Grab("info.picocli:picocli-groovy:4.7.3"),
        @Grab("org.apache.commons:commons-lang3:3.12.0"),
])
@GrabConfig(systemClassLoader = true)

@Command(name = "SessionMunger",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "@|bold SessionMunger|@ rootPath")
@PicocliScript2

// @Option(names = ["-f", "--file"], description = "Indicates a file containing the list of classes to be processed")
// @Field File file

@Parameters(index = "0", description = "The root path to process.", defaultValue = "/opt/data/archive/XNAT_01/arc001")
@Field String rootValue

/*
File root = Paths.get(rootValue).toFile()

root.traverse(type: FileType.FILES, nameFilter: ~/scan_[0-9]+_catalog\.xml$/) {catalogFile ->
    def slurper = new XmlSlurper()
    def catalog = slurper.parse(catalogFile)
    def entryCount = catalog.entries.'*'.size()
    println "Processing catalog ${catalogFile} with ${entryCount} entries"
    /*
    def duplicate = new NodeBuilder().article(id: '5') {
        title('Traversing XML in the nutshell')
        author {
            firstname('Martin')
            lastname('Schmidt')
        }
        'release-date'('2019-05-18')
    }
    articles.append(articleNode)
}
    */

def catalogFile = new File("/Users/rickherrick/Development/XNAT/Home/scan_5_catalog.xml")
def catalog = new XmlSlurper().parse(catalogFile)
def entries = catalog.entries
def entryCount = entries.'*'.size()
println "Processing catalog ${catalogFile} with ${entryCount} entries"

def updated = new XmlSlurper().parseText(XmlUtil.serialize(catalog))
