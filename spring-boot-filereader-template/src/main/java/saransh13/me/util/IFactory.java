package saransh13.me.util;



public interface IFactory<T>
{
    T newObject(String[] split);
}
