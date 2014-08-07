package=transitplay
version=0
release=$(package)-$(version)

outdir=out
srcdir_main=src/main

JAR=jar
JAVAC=javac
JAVA=java
JAVADOC=javadoc

JAVACFLAGS=-g -Xlint:unchecked

java_src=$(shell find $1 -type f -and -name '*.java')
java_resources=$(shell find $1 -type f -and -not -name '*.java')
topath=$(subst $(eval) ,:,$1)
classpath=$(patsubst %,-classpath %,$(call topath,$(filter-out %-sources.jar,$(filter %.jar,$^))))

src_main:=$(call java_src,$(srcdir_main))
resources_main:=$(call java_resources,$(srcdir_main))

all: jars

jars: $(outdir)/$(release).jar

include libs/main.mk

libs/%.mk: %.dependencies
	rm -rf libs/$*
	mkdir -p libs/$*
	tools/sm-download $< libs/$*
	echo 'libs_$*=$$(filter-out %-source.jar,$$(wildcard libs/$*/*.jar))' > $@

$(outdir)/$(release).jar: $(src_main) $(libs_main)
$(outdir)/$(release).jar: $(resources_main:$(srcdir_main)/%=$(outdir)/$(release)/%)
$(outdir)/$(release).jar: $(libs_main)

%.jar:
	@mkdir -p $*/
	$(JAVAC) $(JAVACFLAGS) $(classpath) -d $*/ $(filter %.java,$^)
	$(JAR) -cf$(JARFLAGS) $@ -C $* .

$(outdir)/$(release)/%: $(srcdir_main)/%
	@mkdir -p $(dir $@)
	cp $< $(dir $@)

clean:
	rm -rf $(outdir)

.PHONY: all jars clean
