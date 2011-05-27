package net.minecraft.server;

public class EntityItem extends Entity {

    public ItemStack itemStack;
    private int e;
    public int b = 0;
    public int pickupDelay;
    private int f = 5;
    public float d = (float) (Math.random() * 3.141592653589793D * 2.0D);

    public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
        super(world);
        this.b(0.25F, 0.25F);
        this.height = this.width / 2.0F;
        this.setPosition(d0, d1, d2);
        this.itemStack = itemstack;
        this.yaw = (float) (Math.random() * 360.0D);
        this.motX = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.motY = 0.20000000298023224D;
        this.motZ = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    }

    protected boolean n() {
        return false;
    }

    public EntityItem(World world) {
        super(world);
        this.b(0.25F, 0.25F);
        this.height = this.width / 2.0F;
    }

    protected void b() {}

    public void p_() {
        super.p_();
        if (this.pickupDelay > 0) {
            --this.pickupDelay;
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.03999999910593033D;
        if (this.world.getMaterial(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) == Material.LAVA) {
            this.motY = 0.20000000298023224D;
            this.motX = (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.motZ = (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.world.makeSound(this, "random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }

        this.g(this.locX, this.locY, this.locZ);
        this.move(this.motX, this.motY, this.motZ);
        float f = 0.98F;

        if (this.onGround) {
            f = 0.58800006F;
            int i = this.world.getTypeId(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ));

            if (i > 0) {
                f = Block.byId[i].frictionFactor * 0.98F;
            }
        }

        this.motX *= (double) f;
        this.motY *= 0.9800000190734863D;
        this.motZ *= (double) f;
        if (this.onGround) {
            this.motY *= -0.5D;
        }

        ++this.e;
        ++this.b;
        if (this.b >= 6000) {
            this.die();
        }
    }

    public boolean f_() {
        return this.world.a(this.boundingBox, Material.WATER, this);
    }

    protected void a(int i) {
        this.damageEntity((Entity) null, i);
    }

    public boolean damageEntity(Entity entity, int i) {
        this.ac();
        this.f -= i;
        if (this.f <= 0) {
            this.die();
        }

        return false;
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Health", (short) ((byte) this.f));
        nbttagcompound.a("Age", (short) this.b);
        nbttagcompound.a("Item", this.itemStack.a(new NBTTagCompound()));
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.f = nbttagcompound.d("Health") & 255;
        this.b = nbttagcompound.d("Age");
        NBTTagCompound nbttagcompound1 = nbttagcompound.k("Item");

        this.itemStack = new ItemStack(nbttagcompound1);
    }

    public void b(EntityHuman entityhuman) {
        if (!this.world.isStatic) {
            int i = this.itemStack.count;

            if (this.pickupDelay == 0 && entityhuman.inventory.canHold(this.itemStack)) {
                if (this.itemStack.id == Block.LOG.id) {
                    entityhuman.a((Statistic) AchievementList.g);
                }

                if (this.itemStack.id == Item.LEATHER.id) {
                    entityhuman.a((Statistic) AchievementList.t);
                }

                this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityhuman.receive(this, i);
                this.die();
            }
        }
    }
}
