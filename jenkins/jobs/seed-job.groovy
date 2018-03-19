import jenkins.model.Jenkins;
import hudson.model.FreeStyleProject;
import hudson.tasks.Shell;
import javaposse.jobdsl.dsl.DslScriptLoader;

job = Jenkins.instance.createProject(FreeStyleProject, 'job-name')

job.buildersList.add(new Shell('echo hello world'))
def scriptLoader = new DslScriptLoader()
scriptLoader.scriptText("""
job('my-job')
""")
job.buildersList.add(scriptLoader)

job.save()

//-------------------------------------------------------------
