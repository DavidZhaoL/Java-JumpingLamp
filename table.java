import com.jogamp.opengl.*;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;

public class table extends Model{
    private Vec3 scale;
    private Vec3 translate;
    private Model[] tableLeg;
    private Model tableSurface;
    public table(GL3 gl, Camera camera, Light light, Light light1, Shader shader, Material material, Mesh mesh, int[] textureId1, int[] textureId2, Vec3 scale,Vec3 translate)
    {
        super(gl,camera,light,light1,shader,material,null,mesh,textureId1,textureId2);
        this.scale=scale;
        this.translate=translate;
        tableLeg=new Model[4];

        setTable(gl);
    }

    public void setTable(GL3 gl)
    {
        modelMatrix = Mat4.multiply(Mat4Transform.translate(-4, 2f, 3), Mat4Transform.scale(1, 4, 1));
        tableLeg[0] = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh, textureId1);

        modelMatrix = Mat4.multiply(Mat4Transform.translate(4,2f,3),Mat4Transform.scale(1,4,1));
        tableLeg[1] = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh,  textureId1);

        modelMatrix = Mat4.multiply( Mat4Transform.translate(-4,2f,7),Mat4Transform.scale(1,4,1));
        tableLeg[2] = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh,  textureId1);

        modelMatrix = Mat4.multiply(Mat4Transform.translate(4,2f,7),Mat4Transform.scale(1,4,1));
        tableLeg[3] = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh,  textureId1);

        //this is the table surface object
        modelMatrix = Mat4.multiply( Mat4Transform.translate(0,4f,7),Mat4Transform.scale(18,0.3f,12));
        tableSurface = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh, textureId2);
    }

    public void renderTable(GL3 gl)
    {
        for(int i=0;i<4;i++)
        {
            tableLeg[i].render(gl);
        }
        tableSurface.render(gl);
    }
}
