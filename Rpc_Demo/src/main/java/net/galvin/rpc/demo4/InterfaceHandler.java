package net.galvin.rpc.demo4;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * Created by qchu on 16-12-5.
 */
public class InterfaceHandler extends ClassLoader implements Opcodes {

    public static void main(String[] args) throws Exception {
        ISayHello iSayHello = (ISayHello)MakeClass(ISayHello.class);

        iSayHello.MethodA();
        iSayHello.MethodB();
        iSayHello.Abs();
    }

    public static Object MakeClass(Class clazz) throws Exception {
        String name = clazz.getSimpleName();
        String className = name + "$imp";

        String Iter = clazz.getName().replaceAll("\\.", "/");

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_5,Opcodes.ACC_PUBLIC+Opcodes.ACC_SUPER, className, null, "java/lang/Object", new String[] {Iter});

        //空构造
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        Method[] methods = clazz.getMethods();
        for (Method method : methods)
        {
            makeMethod(cw, method.getName(), className);
        }

        cw.visitEnd();
        /*
         * 写入文件
         */
        byte[] code = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(className);
        fos.write(code);
        fos.close();

        /*
         * 从文件加载类
         */
        InterfaceHandler loader = new InterfaceHandler();
        Class exampleClass = loader.defineClass(className,code,0,code.length);

        /*
         * 反射生成实例
         */
        Object obj = exampleClass.getConstructor(null).newInstance(null);

        return obj;
    }

    private static void makeMethod(ClassWriter cw, String methodName, String className){
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, "()V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(8, l0);
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("调用方法 [" + methodName + "]");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitLineNumber(9, l1);
        mv.visitInsn(Opcodes.RETURN);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l2, 0);
        mv.visitMaxs(2,1);
        mv.visitEnd();
    }

}
