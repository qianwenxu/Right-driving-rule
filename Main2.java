/*import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

class MyComparator implements Comparator<car>{
	 @Override
	 public int compare(car o1, car o2) {
	//���n1С��n2�����Ǿͷ�����ֵ�����n1����n2���Ǿͷ��ظ�ֵ��//�����ߵ�һ�£��Ϳ���ʵ�ַ���������
		 if(o1.dist < o2.dist) { 
			 return 1;
	     }else if(o1.dist > o2.dist){
	    	 return -1;
	     }else {
	    	 return 0;
	     }
	    }    
	 }
public class Main2 {
	 static boolean is_overtaking_need(int j,car[] eachcar,int rightbefore){
		 double safety_dist=eachcar[j].sd;
		 double danger_dist=eachcar[j].sd;
		 if(rightbefore!=-1){
			 if(eachcar[rightbefore].dist-eachcar[j].dist<=safety_dist&&eachcar[rightbefore].v<eachcar[j].want_v){
				 if(eachcar[rightbefore].dist-eachcar[j].dist<danger_dist&&eachcar[j].lane==1){
					 System.out.println("Σ�գ�");
				 }
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_to_left(int j,car[] eachcar,int carnum,int outcar){
		 double safety_dist=eachcar[j].sd;
		 int leftbefore=-1;
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==2){
				 leftbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 int leftafter=-1;
			 for(int s=j+1;s<carnum;s++){
				 if(eachcar[s].lane==2){
					 leftafter=s;//���г�
					 break;
				 }
			 }
			 if(leftafter==-1||eachcar[j].dist-eachcar[leftafter].dist-eachcar[leftafter].v>safety_dist){
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_to_right(int j,car[] eachcar,int carnum,int outcar){
		 double safety_dist=eachcar[j].sd;
		 int rightbefore=-1;
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==1){
				 rightbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(rightbefore==-1||eachcar[rightbefore].dist-eachcar[j].dist>safety_dist){
			 int rightafter=-1;
			 for(int s=j+1;s<carnum;s++){
				 if(eachcar[s].lane==1){
					 rightafter=s;//���г�
					 break;
				 }
			 }
			 if(rightafter==-1||eachcar[j].dist-eachcar[rightafter].dist-eachcar[rightafter].v>safety_dist){
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static int is_left_following(int j,car[] eachcar,int carnum,int outcar){
		 double danger_dist=eachcar[j].sd;
		 double safety_dist=eachcar[j].sd;
		 int leftbefore=-1;//��¼���ǰ��
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==2){
				 leftbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 return 0;
		 }else{
			 if(eachcar[leftbefore].dist-eachcar[j].dist<danger_dist){
				 System.out.println("Σ�գ�");
			 }
			 return eachcar[leftbefore].v;
		 }
		 
	 }
	 public static void main(String[] args){
		 int carnum=0;
		 int outcar=0;//��ʻ���ĳ���
		 int highway_length=8000;//8km
		 int safety_dist=100;//100m
		 int max_car_num=400;
		 int overtaking_dist=800;
		 car eachcar[]=new car[max_car_num];
		 int left_v=33;//��೵���ٶ�
		 int overtake_num=0;//��¼��������
		 int return_num=0;
		 double is_busy=0.5;
		 //int danger_dist=40;//Σ�վ���40m
		 for(int i=0;i<1000;i++){//ʱ��
			 Random r = new Random();
			 double u = r.nextDouble();
			 if(u<is_busy&&(carnum==0||eachcar[carnum-1].dist>safety_dist)){
				 eachcar[carnum]=new car();
				 eachcar[carnum].dist=0;
				 eachcar[carnum].lane=1;
				 eachcar[carnum].v=(int)Math.floor(r.nextDouble()*16)+17;
				 eachcar[carnum].want_v=eachcar[carnum].v;
				 eachcar[carnum].i=carnum;
				 eachcar[carnum].tr=r.nextDouble();
				 eachcar[carnum].sd=eachcar[carnum].tr*eachcar[carnum].v+0.004925*eachcar[carnum].v*eachcar[carnum].v;
				 //System.out.println(carnum+"�ٶ�:"+eachcar[carnum].v);
				 carnum+=1;
			 }
			 for(int j=outcar;j<carnum;j++){//�Ŷӵĳ�
				 if(eachcar[j].lane==1){
					 eachcar[j].dist+=eachcar[j].v;//�Ҳ೵�� 
					 if(eachcar[j].dist>highway_length){
						 outcar++;
						 eachcar[j].dist=-1;
						 continue;
					 }
					 int rightbefore=-1;//��¼�Ҳ�ǰ��
					 if(j!=0){
						 for(int s=j-1;s>=outcar;s--){
							 if(eachcar[s].lane==1){
								 rightbefore=s;//ǰ���г�
								 break;
							 }
						 }
						 if(is_overtaking_need(j,eachcar,rightbefore)){
							 if(is_to_left(j,eachcar,carnum,outcar)){
								 eachcar[j].lane=2;
								 eachcar[j].v=eachcar[rightbefore].v+6;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
								 overtake_num++;
								 System.out.println("��"+i+"��"+eachcar[j].i+"����");
							 }else{
								 if(rightbefore!=-1){
									 eachcar[j].v = eachcar[rightbefore].v;
									 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
								 }
							 }
						 }
					 }
				 }else{
					 eachcar[j].dist+=left_v;//���
					 if(eachcar[j].dist>highway_length){
						 outcar++;
						 eachcar[j].dist=-1;
						 continue;
					 }
					 int rightbefore=-1;//��¼�Ҳ�ǰ��
					 for(int s=j-1;s>=outcar;s--){
						 if(eachcar[s].lane==1){
							 rightbefore=s;//ǰ���г�
							 break;
						 }
					 }
					 if(is_overtaking_need(j,eachcar,rightbefore)){
						 int following_v=is_left_following(j,eachcar,carnum,outcar);
						 if(following_v!=0){
							 eachcar[j].v=following_v;
							 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
						 }
					 }else{
						 if(is_to_right(j,eachcar,carnum,outcar)){
							 eachcar[j].lane=1;
							 eachcar[j].v=eachcar[j].want_v;
							 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
							 return_num++;
							 System.out.println("��"+i+"����"+eachcar[j].i+"���ص��Ҳ࣬����Ϊ��"+j+"��");
						 }else{
							 int following_v=is_left_following(j,eachcar,carnum,outcar);
							 if(following_v!=0){
								 eachcar[j].v=following_v;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.004925*eachcar[j].v*eachcar[j].v;
							 } 
						 }
					 }
				 }
				 Comparator cmp = new MyComparator();
			     Arrays.sort(eachcar,outcar,carnum,cmp);
			 }
		 }
		 System.out.println("���г�������"+carnum);
		 System.out.println("����������"+overtake_num);
		 System.out.println("�������ȥ�Ĵ�����"+return_num);
		 System.out.println("��ʻ����ͷ��������"+outcar);
	 }
}
*/