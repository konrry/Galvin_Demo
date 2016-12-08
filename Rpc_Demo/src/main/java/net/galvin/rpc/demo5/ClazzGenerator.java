package net.galvin.rpc.demo5;

import net.galvin.rpc.comm.HelloService;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 字节码生成器，为指定的接口生成一个实现类，作为代理类
 */
public class ClazzGenerator extends ClassLoader {

    private static ClazzGenerator clazzGenerator = null;

    public static ClazzGenerator get(){
        if(clazzGenerator == null){
            synchronized (ClazzGenerator.class){
                if(clazzGenerator == null){
                    clazzGenerator = new ClazzGenerator();
                }
            }
        }
        return clazzGenerator;
    }

    /**
     * 根据Class返回一个实现类
     */
    public Object build(Class tClass) throws IOException, IllegalAccessException, InstantiationException {
        String name = tClass.getSimpleName();
        String className = name + "$OrangeOrange";

        String Iter = tClass.getName().replaceAll("\\.", "/");

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classWriter.visit(Opcodes.V1_5,Opcodes.ACC_PUBLIC+Opcodes.ACC_SUPER, className, null, "java/lang/Object", new String[] {Iter});

        //构造器
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        //接口中类的方法
        Method[] methodArr = tClass.getMethods();
        for(Method method : methodArr){
            MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC,method.getName(),"(Ljava/lang/String;)Ljava/lang/String;",null,null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(Opcodes.ALOAD,1);
            methodVisitor.visitInsn(Opcodes.ARETURN);
            methodVisitor.visitMaxs(2,2);
            methodVisitor.visitEnd();
        }

        classWriter.visitEnd();


        byte[] code = classWriter.toByteArray();
        File file = new File("d:/"+className+".class");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(code);
        fos.close();

        ClazzGenerator clazzGenerator = new ClazzGenerator();
        Class exampleClass = clazzGenerator.defineClass(className,code,0,code.length);
        HelloService helloService = (HelloService) exampleClass.newInstance();
        System.out.println(helloService.hello("Galvin"));
        return null;
    }


    public static void main(String[] args) throws Exception {
        ClazzGenerator.get().build(HelloService.class);
    }


}
