import com.jogamp.opengl.*;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;

public class objOnTable extends Model {

    private Model[] obj;

    public objOnTable(GL3 gl, Camera camera, Light light, Light light1, Shader shader, Material material, Mat4 modelMatrix,Mesh mesh, int[] textureId1, int[] textureId2) {
        super(gl, camera, light, light1, shader, material, modelMatrix, mesh, textureId1, textureId2);
        obj=new Model[2];
        setObj(gl);
    }
    public void setObj(GL3 gl)
    {
        modelMatrix = Mat4.multiply(Mat4Transform.translate(8f,4.3f,2.5f),Mat4Transform.scale(1f,0.5f,1f));
        obj[0] = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh, textureId1);

        modelMatrix = Mat4.multiply(Mat4Transform.translate(8f,4.8f,2.5f),Mat4Transform.scale(0.5f,0.5f,0.5f));
        obj[1] = new Model(gl, camera, light,light1, shader, material, modelMatrix, mesh, textureId1);
    }

    public void render(GL3 gl)
    {
        obj[0].render(gl);
        obj[1].render(gl);
    }
}
