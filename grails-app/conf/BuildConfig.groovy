grails.project.work.dir = 'target'
grails.project.docs.output.dir = 'docs/manual'
grails.project.source.level = 1.6

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		runtime 'com.caucho:hessian:4.0.38'
		runtime 'org.springframework:spring-aop:4.0.9.RELEASE'
		runtime 'org.springframework:spring-expression:4.0.9.RELEASE'

	}

	plugins {
		build(':rest-client-builder:2.1.1') {
			export = false
		}
	}
}
