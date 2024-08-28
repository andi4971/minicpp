package org.azauner.ast.generator.aspect

import org.antlr.v4.runtime.ParserRuleContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.azauner.ast.generator.exception.SemanticException
import kotlin.system.exitProcess

@Aspect
class SemanticErrorAspect {

    companion object {
        @JvmStatic
        var isEnabled: Boolean = true
    }

    @Pointcut("execution(* org.azauner.ast.generator.visitor..*.visit*(..))")
    fun visitorPointcut() = Unit

    @Around("visitorPointcut()")
    fun addErrorHandling(joinPoint: ProceedingJoinPoint): Any {
        if(!isEnabled) {
            return joinPoint.proceed()
        }
        //todo potentially refactor to not use exceptions but static variable with errors
        //problem still remains then when required functions or variables are not found
        //maybe return a dummy variable or function in this case
        return try {
            joinPoint.proceed()
        }catch (e: SemanticException) {
            val ctx = joinPoint.args.first() as ParserRuleContext
            System.err.println("Error on line ${ctx.start.line}:${ctx.start.charPositionInLine} : ${e.message}")
            exitProcess(1)
        }
    }
}
