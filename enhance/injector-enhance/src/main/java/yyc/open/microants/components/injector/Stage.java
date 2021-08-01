package yyc.open.microants.components.injector;

/**
 * {@link Stage} we're running in.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/8/1
 */
public enum Stage {
    /**
     * We're running in a tool (an IDE plugin for example). We need binding meta data but not a
     * functioning Injector. Do not inject members of instances. Do not load eager singletons. Do as
     * little as possible so our tools run nice and snappy. Injectors created in this stage cannot be
     * used to satisfy injections.
     */
    TOOL,

    /**
     * We want fast startup times at the expense of runtime performance and some up front error
     * checking.
     */
    DEVELOPMENT,

    /**
     * We want to catch errors as early as possible and take performance hits up front.
     */
    PRODUCTION
}
